package com.example.onlineshop.controller;

import com.example.onlineshop.DTO.ProductDTO;
import com.example.onlineshop.DTO.ReviewDTO;
import com.example.onlineshop.service.ProductService;
import com.example.onlineshop.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ReviewService reviewService;
    @GetMapping("/{page}/{size}")
    public ResponseEntity<Optional<Page<ProductDTO>>> getProductList(HttpServletRequest request, @PathVariable @NotNull
                                                                     @Min(value = 0) Integer page, @PathVariable @Min(value = 1)
                                                                     @NotNull Integer size){
        Optional<String> name = Optional.ofNullable(request.getHeader("Name"));
        Optional<String> minPrice = Optional.ofNullable(request.getHeader("Min-Price"));
        Optional<String> maxPrice = Optional.ofNullable(request.getHeader("Max-Price"));
        Optional<String> description = Optional.ofNullable(request.getHeader("Description"));
        Optional<String> quantity = Optional.ofNullable(request.getHeader("Quantity"));
        String categoryName = request.getHeader("Category");
        String brandName = request.getHeader("Brand");
        Optional<Page<ProductDTO>> resultPage = productService.getProductList(name, minPrice, maxPrice, categoryName,
                description, quantity, brandName, page, size);
        return ResponseEntity.ok(resultPage);
    }

    @GetMapping("/{productName}")
    public ResponseEntity<ProductDTO> getProductInfoByName(@PathVariable @Valid @NotBlank String productName){
        return ResponseEntity.ok(productService.getProductInfoByName(productName));
    }

    @GetMapping("/{productName}/reviews")
    public ResponseEntity<Optional<Page<ReviewDTO>>> getProductsReviews(@PathVariable @Valid @NotBlank String productName){
        return ResponseEntity.ok(reviewService.getReviewList(productName));
    }
}
