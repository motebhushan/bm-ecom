package com.ecommerse.project.service;

import com.ecommerse.project.model.Category;
import com.ecommerse.project.payload.CategoryDTO;
import com.ecommerse.project.payload.CategoryResponce;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

public interface CategoryService {
//    public List<Category>categories=new ArrayList<>();
    public CategoryResponce getAllCategories(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder);
    public CategoryDTO createCategory(CategoryDTO categoryDTO);
    public CategoryDTO deleteCategory(Long categoryid);
    public CategoryDTO updateCategory( CategoryDTO categoryDTO, Long categoryid);

    }
