package com.example.onlineshop.DTO;

import com.example.onlineshop.entity.Authority;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
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
