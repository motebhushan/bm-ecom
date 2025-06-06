package com.ecommerse.project.service;

import com.ecommerse.project.model.Category;
import com.ecommerse.project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryService {
//    public List<Category>categories=new ArrayList<>();
    @Autowired
    final CategoryRepository categoryRepository;

    public CategoryServiceImp(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public String createCategory(Category category) {
       categoryRepository.save(category);
        return "added successfully";
    }

    @Override
    public String deleteCategory(Long categoryid) {
       categoryRepository.deleteById(categoryid);
        return "Deleted successfully";
    }

    @Override
    public String updateCategory(Category category, Long categoryid) {
      Optional<Category> temp=categoryRepository.findById(categoryid);
      if(temp.isPresent()){
          Category existingCategory=temp.get();
          existingCategory.setCategoryName(category.getCategoryName());
          categoryRepository.save(existingCategory);
          return "category updated  succesfully";
      }
        return "category not found  ";
    }
}
