package com.trinhnx151.human_resource_management.service.impl;

import com.trinhnx151.human_resource_management.dto.sdi.image.UploadImageSdi;
import com.trinhnx151.human_resource_management.entity.Employee;
import com.trinhnx151.human_resource_management.entity.Image;
import com.trinhnx151.human_resource_management.exception.custom.ImageErrorException;
import com.trinhnx151.human_resource_management.repository.EmployeeRepo;
import com.trinhnx151.human_resource_management.repository.ImageRepo;
import com.trinhnx151.human_resource_management.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Stream;

@Service
public class StorageServiceImpl implements StorageService {

    private final Path storageFolder = Paths.get("uploads");

    @Autowired
    private ImageRepo imageRepo;

    @Value("${project.image}")
    String path;
    @Autowired
    private EmployeeRepo employeeRepo;

    public StorageServiceImpl() {
        try {
            Files.createDirectories(storageFolder);
        } catch (IOException exception) {
            throw new ImageErrorException("Cannot initialize storage");
        }
    }

    private boolean isImageFile(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        assert fileExtension != null;
        return Arrays.asList(new String[]{"png", "jpg", "jpeg", "bmp"})
                .contains(fileExtension.trim().toLowerCase());
    }

    @Override
    public String storeFile(MultipartFile file, HttpSession session) {
        try {
            this.validateImage(file);
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String generatedFileName = UUID.randomUUID().toString().replace("-", "");
            generatedFileName = generatedFileName + "." + fileExtension;
            Path destinationFilePath = this.storageFolder.resolve(
                            Paths.get(generatedFileName))
                    .normalize().toAbsolutePath();
            if (!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())) {
                throw new ImageErrorException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            Optional <Employee> employee = (Optional<Employee>) session.getAttribute("employee");
            employee.get().setImage("http://localhost:8080/api/v1/employee/files/" + generatedFileName);
            employeeRepo.save(employee.get());
            return generatedFileName;
        } catch (IOException exception) {
            throw new ImageErrorException("Failed to store file.");
        }
    }

    private void validateImage(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ImageErrorException("Failed to store empty file.");
        }
        if (!isImageFile(file)) {
            throw new ImageErrorException("You can only upload image file");
        }
        float fileSizeInMegabytes = file.getSize() / 1_000_000.0f;
        if (fileSizeInMegabytes > 5.0f) {
            throw new ImageErrorException("File must be <= 5Mb");
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.storageFolder, 1)
                    .filter(path -> !path.equals(this.storageFolder) && !path.toString().contains("._"))
                    .map(this.storageFolder::relativize);
        } catch (IOException e) {
            throw new ImageErrorException("Failed to load stored files");
        }
    }

    @Override
    public byte[] readFileContent(String fileName) {
        try {
            Path file = storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return StreamUtils.copyToByteArray(resource.getInputStream());
            } else {
                throw new ImageErrorException(
                        "Could not read file: " + fileName);
            }
        } catch (IOException exception) {
            throw new ImageErrorException("Could not read file: " + fileName);
        }
    }

//    @Override
//    public String uploadFileWithBase64(MultipartFile file, HttpSession session) throws IOException {
//        String base64 = imgToBase64(file,session);
//        File f = convertBase64ToFile(base64,path,file.getOriginalFilename(),session);
//        Optional <Employee> employee = (Optional<Employee>) session.getAttribute("employee");
//        assert f != null;
//        employee.get().setImage(f.getPath());
//        employeeRepo.save(employee.get());
//        return f.getPath();
//    }

    @Override
    public String upload(UploadImageSdi uploadImageSdi) {
        try {
            byte[] imageByte = Base64.getDecoder().decode(uploadImageSdi.getBase64());
            String generatedFileName = UUID.randomUUID().toString().replace("-", "");
            generatedFileName = generatedFileName + uploadImageSdi.getName().substring(uploadImageSdi.getName().lastIndexOf("."));
            Path destinationFilePath = this.storageFolder.resolve(
                            Paths.get(generatedFileName))
                    .normalize().toAbsolutePath();
            FileOutputStream fileOutputStream = new FileOutputStream(destinationFilePath.toString());
            fileOutputStream.write(imageByte);
            fileOutputStream.close();
            return "http://localhost:8080/api/v1/employee/files/" + generatedFileName;
        } catch (Exception e) {
            throw new ImageErrorException("Error");
        }

    }

    public String imgToBase64(MultipartFile file) throws IOException {

        InputStream in;
        byte[] data = null;
        //Read picture byte arrays
        try {
            byte[] byteArr = file.getBytes();
            in = new ByteArrayInputStream(byteArr);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e1) {
            e1.getMessage();
            e1.printStackTrace();
        }

        return Base64.getEncoder().encodeToString(data);
    }


}
