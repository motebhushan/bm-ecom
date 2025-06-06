package com.ecommerse.project.controller;

import com.ecommerse.project.model.Category;
import com.ecommerse.project.service.CategoryService;
import com.ecommerse.project.service.CategoryServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;


    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/categories")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/public/addcategory")
    public String createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }
    @DeleteMapping("/public/deletecategory/{categoryid}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryid){
        try{
            return new ResponseEntity<>(categoryService.deleteCategory(categoryid), HttpStatus.OK);
        }catch (ResponseStatusException e){
            return new ResponseEntity<>(e.getReason(),e.getStatusCode());
        }

    }
    @PutMapping("/public/updatecategory/{categoryid}")
    public ResponseEntity<String> updateCategory(@RequestBody Category category,@PathVariable Long categoryid){
        try{
            return new ResponseEntity<>(categoryService.updateCategory(category,categoryid), HttpStatus.OK);
        }catch (ResponseStatusException e){
            return new ResponseEntity<>(e.getReason(),e.getStatusCode());
        }

    }
}

