package com.example.onlineshop.DTO;

import com.example.onlineshop.entity.Brand;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BrandDTO {
    public static BrandDTO from(Brand brand){
        return BrandDTO.builder()
                .id(brand.getId())
                .name(brand.getName())
                .build();
    }

    private Long id;
    private String name;
}
