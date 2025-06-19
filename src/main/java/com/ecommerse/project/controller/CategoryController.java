package com.ecommerse.project.controller;

import com.ecommerse.project.conifg.AppConstant;
import com.ecommerse.project.model.Category;
import com.ecommerse.project.payload.CategoryDTO;
import com.ecommerse.project.payload.CategoryResponce;
import com.ecommerse.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponce> getAllCategories(  @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER) Integer pageNumber,
                                                               @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE) Integer pageSize,
                                                               @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_BY) String sortBy,
                                                               @RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_ORDER) String sortOrder) {
        CategoryResponce dbData=categoryService.getAllCategories(pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(dbData,HttpStatus.OK);
    }

    @PostMapping("/admin/addcategory")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/deletecategory/{categoryid}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryid){
            return new ResponseEntity<>(categoryService.deleteCategory(categoryid), HttpStatus.OK);
//        catch (ResponseStatusException e){
//            return new ResponseEntity<>(e.getReason(),e.getStatusCode());
//        }
    }

    @PutMapping("/admin/updatecategory/{categoryid}")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO,@PathVariable Long categoryid){
            return new ResponseEntity<>(categoryService.updateCategory(categoryDTO,categoryid), HttpStatus.OK);
    }
}

