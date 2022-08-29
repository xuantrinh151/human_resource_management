package com.trinhnx151.human_resource_management.service.impl;

import com.trinhnx151.human_resource_management.entity.Employee;
import com.trinhnx151.human_resource_management.exception.custom.ImageErrorException;
import com.trinhnx151.human_resource_management.repository.EmployeeRepo;
import com.trinhnx151.human_resource_management.service.StorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class StorageServiceImpl implements StorageService {

    private final Path storageFolder = Paths.get("uploads");

    @Autowired
    private EmployeeRepo employeeRepo;

    public StorageServiceImpl() {
        try {
            Files.createDirectories(storageFolder);
        } catch (IOException exception) {
            throw new RuntimeException("Cannot initialize storage", exception);
        }
    }

    private boolean isImageFile(MultipartFile file) {
        //Let install FileNameUtils
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[]{"png", "jpg", "jpeg", "bmp"})
                .contains(fileExtension.trim().toLowerCase());
    }


    @Override
    public String storeFile(MultipartFile file, HttpSession session) {
        try {
            this.validateImage(file);
            //File must be rename, why ?
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String generatedFileName = UUID.randomUUID().toString().replace("-", "");
            generatedFileName = generatedFileName + "." + fileExtension;
            Path destinationFilePath = this.storageFolder.resolve(
                            Paths.get(generatedFileName))
                    .normalize().toAbsolutePath();
            if (!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())) {
                throw new RuntimeException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            Optional<Employee> employee = (Optional<Employee>) session.getAttribute("employee");
            employee.get().setImage(generatedFileName);
            employeeRepo.save(employee.get());
            return generatedFileName;
        } catch (IOException exception) {
            throw new RuntimeException("Failed to store file.", exception);
        }
    }

    private void validateImage(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ImageErrorException("Failed to store empty file.");
        }
        //check file is image ?
        if (!isImageFile(file)) {
            throw new ImageErrorException("You can only upload image file");
        }
        //file must be <= 5Mb
        float fileSizeInMegabytes = file.getSize() / 1_000_000.0f;
        if (fileSizeInMegabytes > 5.0f) {
            throw new ImageErrorException("File must be <= 5Mb");
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            //list all files in storageFolder
            //How to fix this ?
            return Files.walk(this.storageFolder, 1)
                    .filter(path -> !path.equals(this.storageFolder) && !path.toString().contains("._"))
                    .map(this.storageFolder::relativize);
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to load stored files", e);
        }
    }

    @Override
    public byte[] readFileContent(String fileName) {
        try {
            Path file = storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return bytes;
            }
            else {
                throw new RuntimeException(
                        "Could not read file: " + fileName);
            }
        }
        catch (IOException exception) {
            throw new RuntimeException("Could not read file: " + fileName, exception);
        }
    }


}
