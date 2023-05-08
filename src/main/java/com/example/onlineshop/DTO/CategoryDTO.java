package com.example.onlineshop.DTO;

import com.example.onlineshop.entity.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CategoryDTO {
    public static CategoryDTO from(Category category){
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    private Long id;
    private String name;
}
