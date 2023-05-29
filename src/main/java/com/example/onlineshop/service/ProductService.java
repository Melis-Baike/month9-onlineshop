package com.example.onlineshop.service;

import com.example.onlineshop.DTO.ProductDTO;
import com.example.onlineshop.entity.Brand;
import com.example.onlineshop.entity.Category;
import com.example.onlineshop.entity.Product;
import com.example.onlineshop.repository.BrandRepository;
import com.example.onlineshop.repository.CategoryRepository;
import com.example.onlineshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    public Optional<Page<ProductDTO>> getProductList(Optional<String> name, Optional<String> minPrice,
                                                     Optional<String> maxPrice, String categoryName,
                                                     Optional<String> description,
                                                     Optional<String> quantity, String brandName, Integer pageNumber,
                                                     Integer size){
        Sort sort = Sort.by(Sort.Order.asc("name"));
        Pageable pageable = PageRequest.of(pageNumber, size, sort);
        Optional<Category> category = categoryRepository.findByName(categoryName);
        Optional<Brand> brand = brandRepository.findByName(brandName);
        Page<Product> page = productRepository.searchProducts(name, minPrice, maxPrice, category, quantity, brand,
                description, pageable);
        return Optional.of(page.map(ProductDTO::from));
    }

    public ProductDTO getProductInfoByName(String productName){
        Product product = productRepository.findByName(productName).get();
        return ProductDTO.from(product);
    }
}
