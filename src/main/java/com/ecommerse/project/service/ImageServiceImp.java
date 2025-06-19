package com.ecommerse.project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageServiceImp implements ImageService{

    @Override
    public String saveImage(String path, MultipartFile image) throws IOException {
        String originalName = image.getOriginalFilename();
        if (originalName == null) {
            throw new IOException("Invalid file name");
        }

        String uniqueName = UUID.randomUUID().toString();
        String extension = originalName.substring(originalName.lastIndexOf('.'));
        String fileName = uniqueName + extension;

        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String filePath = path + File.separator + fileName;
        Files.copy(image.getInputStream(), Paths.get(filePath));

        return fileName;
    }
}
