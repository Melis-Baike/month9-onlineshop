package com.example.onlineshop.service;

import com.example.onlineshop.DTO.ProductDTO;
import com.example.onlineshop.entity.Category;
import com.example.onlineshop.entity.Product;
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

    public Optional<Page<ProductDTO>> getProductList(Optional<String> name, Optional<String> stringPrice, String categoryName){
        Sort sort = Sort.by(Sort.Order.asc("name"));
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<Product> products;
        Category category;
        if(name.isPresent() && stringPrice.isPresent() && !categoryName.equals("Any") && !stringPrice.get().isBlank()
                && !name.get().isBlank()){
            System.out.println("a");
            Double price = Double.parseDouble(stringPrice.get());
            category = categoryRepository.findByName(categoryName).get();
            products = productRepository.findAllByNameAndPriceAndCategory(name.get(), price,
                    category, pageable);
            return Optional.of(products.map(ProductDTO::from));
        } else if (name.isPresent() && !name.get().isBlank() && stringPrice.isPresent() && !stringPrice.get().isBlank()){
            System.out.println("b");
            Double price = Double.parseDouble(stringPrice.get());
            products = productRepository.findAllByNameAndPrice(name.get(), price, pageable);
            return Optional.of(products.map(ProductDTO::from));
        } else if (name.isPresent() && !name.get().isBlank() && !categoryName.equals("Any")){
            System.out.println("c");
            category = categoryRepository.findByName(categoryName).get();
            products = productRepository.findAllByNameAndCategory(name.get(), category, pageable);
            return Optional.of(products.map(ProductDTO::from));
        } else if (stringPrice.isPresent() && !stringPrice.get().isBlank() && !categoryName.equals("Any")){
            System.out.println("d");
            category = categoryRepository.findByName(categoryName).get();
            Double price = Double.parseDouble(stringPrice.get());
            products = productRepository.findAllByPriceAndCategory(price, category, pageable);
            return Optional.of(products.map(ProductDTO::from));
        } else if (name.isPresent() && !name.get().isBlank()){
            System.out.println("e");
            products = productRepository.findAllByName(name.get(), pageable);
            return Optional.of(products.map(ProductDTO::from));
        } else if (stringPrice.isPresent() && !stringPrice.get().isBlank()){
            System.out.println("f");
            Double price = Double.parseDouble(stringPrice.get());
            products = productRepository.findAllByPrice(price, pageable);
            return Optional.of(products.map(ProductDTO::from));
        } else if (!categoryName.equals("Any")) {
            System.out.println("g");
            category = categoryRepository.findByName(categoryName).get();
            products = productRepository.findAllByCategory(category, pageable);
            return Optional.of(products.map(ProductDTO::from));
        } else {
            System.out.println("h");
            return Optional.empty();
        }
    }

    public ProductDTO getProductInfoId(Long id){
        Product product = productRepository.findById(id).get();
        return ProductDTO.from(product);
    }

    public ProductDTO getProductInfoByName(String productName){
        Product product = productRepository.findByName(productName).get();
        return ProductDTO.from(product);
    }
}
