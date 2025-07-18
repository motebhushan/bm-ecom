package com.ecommerse.project.service;

import com.ecommerse.project.exceptions.APIException;
import com.ecommerse.project.exceptions.ResourceNotFound;
import com.ecommerse.project.model.Category;
import com.ecommerse.project.model.Product;
import com.ecommerse.project.payload.ProductDTO;
import com.ecommerse.project.payload.ProductResponce;
import com.ecommerse.project.repositories.CategoryRepository;
import com.ecommerse.project.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductServiceImp implements ProductService{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private ImageServiceImp ImageServiceImp;

    @Value("${project.path}")
    private String path ;
    @Value("${image.base.url}")
    private String imageBaseUrl;

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFound("category", "categoryId", categoryId));

        List<Product> products = category.getProducts();
        boolean isPresent = false;

        for (Product value : products) {
            if (Objects.equals(value.getProductName(), product.getProductName())) { // Prefer product name
                isPresent = true;
                break;
            }
        }

        if (!isPresent) {
            product.setCategory(category);
            double specialPrice = product.getPrice() - (product.getDiscount() * 0.01) * product.getPrice();
            product.setSpecialPrice(specialPrice);

            Product savedProduct = productRepository.save(product);
            return modelMapper.map(savedProduct, ProductDTO.class);
        } else {
            throw new APIException("Product already present in this category");
        }
    }



    @Override
    public ProductResponce getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, String keyword, String category) {

        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();


        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Specification<Product>spec=Specification.where(null);

        if(keyword!=null&&!keyword.isEmpty()){
            spec=spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("productName")),"%"+keyword.toLowerCase()+"%" ));
        }

        if(category!=null&&!category.isEmpty()){
            spec=spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("category").get("categoryName"),category));
        }

        Page<Product> productPage = productRepository.findAll(spec,pageable);


        List<ProductDTO> productDTOs = productPage.getContent()
                .stream()
                .map(product -> {
                    ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
                    productDTO.setImage(constructImageUrl(product.getImage()));
                    return productDTO;
                })
                .collect(Collectors.toList());

        if (productDTOs.isEmpty()) {
            throw new APIException("No products are available.");
        }


        ProductResponce response = new ProductResponce();
        response.setContent(productDTOs);
        response.setPageNumber(productPage.getNumber());
        response.setPageSize(productPage.getSize());
        response.setTotalElements(productPage.getTotalElements());
        response.setTotalPages(productPage.getTotalPages());
        response.setIsLast(productPage.isLast());

        return response;
    }
    private String constructImageUrl(String imageName) {
        return imageBaseUrl.endsWith("/") ? imageBaseUrl + imageName : imageBaseUrl + "/" + imageName;
    }

    @Override
    public ProductResponce getproductByCategory(long categoryId,Integer pageNumber,Integer pageSize,String sortBy,String sortOrder) {
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFound("category","categoryId",categoryId));

        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Product> productPage = productRepository.findByCategoryOrderByPriceAsc(category,pageable);

        List<Product>products=productPage.getContent();
        List<ProductDTO> productDTOS=products.stream()
                .map(product -> modelMapper.map(product,ProductDTO.class))
                .collect(Collectors.toList());
        if(productDTOS.isEmpty()){
            throw new APIException("no item is present");
        }
        ProductResponce res=new ProductResponce();
        res.setContent(productDTOS);
        res.setPageNumber(productPage.getNumber());
        res.setPageSize(productPage.getSize());
        res.setTotalElements(productPage.getTotalElements());
        res.setTotalPages(productPage.getTotalPages());
        res.setIsLast(productPage.isLast());

        return res;
    }

    @Override
    public ProductResponce getproductByKeyword(String keyword,Integer pageNumber,Integer pageSize,String sortBy,String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Product> productPage = productRepository.findByProductNameLikeIgnoreCase(keyword,pageable);

        List<Product>products=productPage.getContent();
        List<ProductDTO> productDTOS=products.stream()
                .map(product -> modelMapper.map(product,ProductDTO.class))
                .collect(Collectors.toList());
        if(productDTOS.isEmpty()){
            throw new APIException("no item is present");
        }
        ProductResponce res=new ProductResponce();
        res.setContent(productDTOS);
        res.setPageNumber(productPage.getNumber());
        res.setPageSize(productPage.getSize());
        res.setTotalElements(productPage.getTotalElements());
        res.setTotalPages(productPage.getTotalPages());
        res.setIsLast(productPage.isLast());
        return res;
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, Long productId) {
        Product product=modelMapper.map(productDTO,Product.class);
        Product productFromDb=productRepository.findById(productId)
                .orElseThrow(()->new ResourceNotFound("product","productId",productId));
        productFromDb.setProductName(product.getProductName());
        productFromDb.setDescription(productFromDb.getDescription());
        productFromDb.setDiscount(productFromDb.getDiscount());
        productFromDb.setPrice(product.getPrice());
        productRepository.save(productFromDb);
        return modelMapper.map(productFromDb,ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product productFromDb=productRepository.findById(productId)
                .orElseThrow(()->new ResourceNotFound("product","productId",productId));

       productRepository.deleteById(productId);
       return modelMapper.map(productFromDb,ProductDTO.class);
    }

    @Override
    public ProductDTO updateImage(Long productId, MultipartFile image) throws IOException {
        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("Image file is empty");
        }

        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFound("product", "productId", productId));


        String imagePath = ImageServiceImp.saveImage(path, image);
        productFromDb.setImage(imagePath);
        productRepository.save(productFromDb);

        return modelMapper.map(productFromDb, ProductDTO.class);
    }



}







