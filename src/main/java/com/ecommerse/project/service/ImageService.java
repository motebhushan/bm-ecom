package com.ecommerse.project.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    String saveImage(String path, MultipartFile image) throws IOException;
}
