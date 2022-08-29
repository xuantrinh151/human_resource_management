package com.trinhnx151.human_resource_management.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {
    String storeFile(MultipartFile file, HttpSession session);

    Stream<Path> loadAll(); //load all file inside a folder

    byte[] readFileContent(String fileName);

}
