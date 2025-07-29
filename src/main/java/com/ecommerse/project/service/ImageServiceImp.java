package com.ecommerse.project.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class ImageServiceImp implements ImageService {

    private final Cloudinary cloudinary;

    @Autowired
    public ImageServiceImp(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String saveImage(String path, MultipartFile image) throws IOException {
        Map<?, ?> result = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
        return result.get("secure_url").toString();
    }
}
