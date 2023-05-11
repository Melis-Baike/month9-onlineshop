package com.example.onlineshop.DTO;

import com.example.onlineshop.entity.Basket;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BasketDTO {
    public static BasketDTO from(Basket basket){
        return BasketDTO.builder()
                .id(basket.getId())
                .user(UserDTO.from(basket.getUser()))
                .build();
    }

    private Long id;
    private UserDTO user;
}
