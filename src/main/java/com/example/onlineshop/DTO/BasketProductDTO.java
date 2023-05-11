package com.example.onlineshop.DTO;

import com.example.onlineshop.entity.BasketProduct;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BasketProductDTO {
    public static BasketProductDTO from(BasketProduct basketProduct){
        return BasketProductDTO.builder()
                .id(basketProduct.getId())
                .basket(BasketDTO.from(basketProduct.getBasket()))
                .productName(basketProduct.getProductName())
                .productImage(basketProduct.getProductImage())
                .productQuantity(basketProduct.getProductQuantity())
                .productDescription(basketProduct.getProductDescription())
                .productPrice(basketProduct.getProductPrice())
                .category(CategoryDTO.from(basketProduct.getCategory()))
                .brand(BrandDTO.from(basketProduct.getBrand()))
                .build();
    }

    private Long id;
    private BasketDTO basket;
    private String productName;
    private String productImage;
    private Long productQuantity;
    private String productDescription;
    private Double productPrice;
    private CategoryDTO category;
    private BrandDTO brand;
}
