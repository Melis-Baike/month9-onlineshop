package com.example.onlineshop.DTO;

import com.example.onlineshop.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RoleDTO {
    public static RoleDTO from(Role role){
        return RoleDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

    private Long id;
    private String name;
}
