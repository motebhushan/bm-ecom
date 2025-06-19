package com.ecommerse.project.service;

import com.ecommerse.project.payload.ProductDTO;
import com.ecommerse.project.payload.ProductResponce;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, ProductDTO product);

    ProductResponce getAllProducts(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder);

    ProductResponce getproductByCategory(long categoryId,Integer pageNumber,Integer pageSize,String sortBy,String sortOrder);

    ProductResponce getproductByKeyword(String keyword,Integer pageNumber,Integer pageSize,String sortBy,String sortOrder);

    ProductDTO updateProduct(ProductDTO product, Long productid);

    ProductDTO deleteProduct(Long categoryId);

    ProductDTO updateImage(Long productId, MultipartFile image) throws IOException;
}
