package com.example.onlineshop.DTO;

import com.example.onlineshop.entity.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductDTO {
    public static ProductDTO from(Product product){
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .image(product.getImage())
                .quantity(product.getQuantity())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(CategoryDTO.from(product.getCategory()))
                .brand(BrandDTO.from(product.getBrand()))
                .build();
    }

    private Long id;
    private String name;
    private String image;
    private Long quantity;
    private String description;
    private Double price;
    private CategoryDTO category;
    private BrandDTO brand;
}
