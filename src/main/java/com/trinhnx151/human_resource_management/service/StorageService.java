package com.trinhnx151.human_resource_management.service;

import com.trinhnx151.human_resource_management.dto.sdi.image.UploadImageSdi;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {
    String storeFile(MultipartFile file, HttpSession session);

    Stream<Path> loadAll(); //load all file inside a folder

    byte[] readFileContent(String fileName);

    //String uploadFileWithBase64(MultipartFile file, HttpSession session) throws IOException;

    String upload(UploadImageSdi uploadImageSdi);
}
