package com.ecommerse.project.service;

import com.ecommerse.project.exceptions.APIException;
import com.ecommerse.project.exceptions.ResourceNotFound;
import com.ecommerse.project.model.Category;
import com.ecommerse.project.payload.CategoryDTO;
import com.ecommerse.project.payload.CategoryResponce;
import com.ecommerse.project.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CategoryServiceImp implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImp(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public CategoryResponce getAllCategories(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder) {
        Sort sortCategory=sortOrder.equalsIgnoreCase("asc")
                ?Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();
        Pageable pageDetails= PageRequest.of(pageNumber,pageSize,sortCategory);
        Page<Category>categoryPage=categoryRepository.findAll(pageDetails);

        List<Category> data = categoryPage.getContent();
        if (data.isEmpty()) {
            throw new APIException("No category available yet");
        }
        List<CategoryDTO> categoryDTOS = data.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
        CategoryResponce categoryResponce = new CategoryResponce();
        categoryResponce.setContent(categoryDTOS);
        categoryResponce.setPageNumber(categoryPage.getNumber());
        categoryResponce.setTotalPages(categoryPage.getTotalPages());
        categoryResponce.setIsLast(categoryPage.isLast());
        categoryResponce.setTotalElements(categoryPage.getTotalElements());
        categoryResponce.setPageSize(categoryPage.getSize());


        return categoryResponce;
    }


    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category categoryFromDb = categoryRepository.findByCategoryName(category.getCategoryName());
        if (categoryFromDb != null)
            throw new APIException("Category with the name " + category.getCategoryName() + " already exists !!!");
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }


    @Override
    public CategoryDTO deleteCategory(Long categoryid) {
        Category deletedCategory = categoryRepository.findById(categoryid)
                .orElseThrow(() -> new ResourceNotFound("Category","Category",categoryid));
        return modelMapper.map(deletedCategory,CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryid) {
        Category category=modelMapper.map(categoryDTO,Category.class);
      Optional<Category> temp=categoryRepository.findById(categoryid);
      if(temp.isPresent()){
          Category existingCategory=temp.get();
          existingCategory.setCategoryName(category.getCategoryName());
         Category category1= categoryRepository.save(existingCategory);
          return modelMapper.map(category1,CategoryDTO.class);
      }else{
         throw new ResourceNotFound("Category","category",categoryid);
      }
//        return "category not found  ";
    }
}
