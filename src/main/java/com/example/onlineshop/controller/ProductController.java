package com.example.onlineshop.controller;

import com.example.onlineshop.DTO.ProductDTO;
import com.example.onlineshop.DTO.ReviewDTO;
import com.example.onlineshop.service.ProductService;
import com.example.onlineshop.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ReviewService reviewService;
    @GetMapping()
    public ResponseEntity<Optional<Page<ProductDTO>>> getProductList(HttpServletRequest request){
        Optional<String> name = Optional.ofNullable(request.getHeader("Name"));
        Optional<String> price = Optional.ofNullable(request.getHeader("Price"));
        String categoryName = request.getHeader("Category");
        Optional<Page<ProductDTO>> page = productService.getProductList(name, price, categoryName);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{productName}")
    public ResponseEntity<ProductDTO> getProductInfoByName(@PathVariable @NotBlank String productName){
        return ResponseEntity.ok(productService.getProductInfoByName(productName));
    }

    @GetMapping("/{productName}/reviews")
    public ResponseEntity<Optional<Page<ReviewDTO>>> getProductsReviews(@PathVariable @NotBlank String productName){
        return ResponseEntity.ok(reviewService.getReviewList(productName));
    }
}
