package com.ecommerse.project.controller;

import com.ecommerse.project.conifg.AppConstant;
import com.ecommerse.project.payload.ProductDTO;
import com.ecommerse.project.payload.ProductResponce;
import com.ecommerse.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO>addProduct(@RequestBody ProductDTO productDTO,
                                                     @PathVariable Long categoryId){
        ProductDTO updatedProductDTO=productService.addProduct(categoryId,productDTO);
        return new ResponseEntity<>(updatedProductDTO, HttpStatus.CREATED);
    }
    @GetMapping("/public/products")
    public ResponseEntity<ProductResponce> getAllProducts(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_BY_Product) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_ORDER) String sortOrder) {

        ProductResponce productResponce = productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder,keyword,category);
        return new ResponseEntity<>(productResponce, HttpStatus.OK);
    }

    @GetMapping("/public/product/getproducts/{categoryId}/category")
    public ResponseEntity<ProductResponce> getProductByCategory(@PathVariable(name="categoryId") long categoryId,
                                                                @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER) Integer pageNumber,
                                                                @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE) Integer pageSize,
                                                                @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_BY_Product) String sortBy,
                                                                @RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_ORDER) String sortOrder){
        ProductResponce productResponce=productService.getproductByCategory(categoryId,pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productResponce,HttpStatus.OK);
    }
    @GetMapping("/public/product/keyword/{keyword}")
    public ResponseEntity<ProductResponce> getProductByKeyword(@PathVariable(name="keyword") String keyword,
                                                               @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER) Integer pageNumber,
                                                               @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE) Integer pageSize,
                                                               @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_BY_Product) String sortBy,
                                                               @RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_ORDER) String sortOrder){
        ProductResponce productResponce=productService.getproductByKeyword("%"+keyword+"%",pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productResponce,HttpStatus.OK);
    }

    @PutMapping("/admin/updateproduct/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO,
                                                    @PathVariable Long productId){
        ProductDTO updatedProductDTO=productService.updateProduct(productDTO,productId);
        return new ResponseEntity<>(updatedProductDTO,HttpStatus.OK);
    }

    @DeleteMapping("/admin/product/{productId}")
    public ResponseEntity<ProductDTO>deleteProduct(@PathVariable Long productId){
        ProductDTO productDTO=productService.deleteProduct(productId);
        return new ResponseEntity<>(productDTO,HttpStatus.OK);
    }
    @PutMapping("/admin/updateImage/{productId}")
    public ResponseEntity<ProductDTO>updateImage(@PathVariable(name = "productId") Long productId,
                                                 @RequestParam(name = "image") MultipartFile image) throws IOException {
        ProductDTO productDTO=productService.updateImage(productId,image);
        return new ResponseEntity<>(productDTO,HttpStatus.OK);
    }

}
