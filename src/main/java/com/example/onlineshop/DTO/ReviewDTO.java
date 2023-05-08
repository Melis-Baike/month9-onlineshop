package com.example.onlineshop.DTO;

import com.example.onlineshop.entity.Review;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ReviewDTO {
    public static ReviewDTO from(Review review){
        return ReviewDTO.builder()
                .id(review.getId())
                .text(review.getText())
                .rating(review.getRating())
                .user(UserDTO.from(review.getUser()))
                .product(ProductDTO.from(review.getProduct()))
                .build();
    }

    private Long id;
    private String text;
    private Double rating;
    private UserDTO user;
    private ProductDTO product;
}
