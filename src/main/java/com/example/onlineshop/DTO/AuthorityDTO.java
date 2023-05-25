package com.example.onlineshop.DTO;

import com.example.onlineshop.entity.Authority;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AuthorityDTO {
    public static AuthorityDTO from(Authority authority){
        return AuthorityDTO.builder()
                .id(authority.getId())
                .name(authority.getName())
                .build();
    }

    private Long id;
    private String name;
}
