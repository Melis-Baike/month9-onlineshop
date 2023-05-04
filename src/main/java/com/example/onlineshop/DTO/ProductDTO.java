package com.example.onlineshop.DTO;

import com.example.onlineshop.entity.Brand;
import com.example.onlineshop.entity.Category;
import com.example.onlineshop.entity.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {
    public static ProductDTO from(Product product){
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .image(product.getImage())
                .quantity(product.getQuantity())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory())
                .brand(product.getBrand())
                .build();
    }

    private Long id;
    private String name;
    private String image;
    private Long quantity;
    private String description;
    private Double price;
    private Category category;
    private Brand brand;
}
