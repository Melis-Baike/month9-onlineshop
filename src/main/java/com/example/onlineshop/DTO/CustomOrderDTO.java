package com.example.onlineshop.DTO;

import com.example.onlineshop.entity.Order;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CustomOrderDTO {
    public static CustomOrderDTO from(Order order, String productImage){
        return CustomOrderDTO.builder()
                .id(order.getId())
                .userDTO(UserDTO.from(order.getUser()))
                .productName(order.getProductName())
                .productImage(productImage)
                .productQuantity(order.getProductQuantity())
                .productPrice(order.getProductPrice())
                .totalPrice(order.getTotalPrice())
                .time(order.getTime())
                .build();
    }

    private Long id;
    private UserDTO userDTO;
    private String productName;
    private String productImage;
    private Long productQuantity;
    private Double productPrice;
    private Double totalPrice;
    private LocalDateTime time;
}
