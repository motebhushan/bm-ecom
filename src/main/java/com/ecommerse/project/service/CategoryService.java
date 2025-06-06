package com.ecommerse.project.service;

import com.ecommerse.project.model.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

public interface CategoryService {
//    public List<Category>categories=new ArrayList<>();
    public List<Category> getAllCategories();
    public String createCategory( Category category);
    public String deleteCategory(Long categoryid);
    public String updateCategory( Category category, Long categoryid);

    }
