package com.example.onlineshop.DTO;

import com.example.onlineshop.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserDTO {
    public static UserDTO from(User user){
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .enabled(user.isEnabled())
                .authorityDTO(AuthorityDTO.from(user.getAuthority()))
                .build();
    }

    private Long id;
    private String name;
    private String email;
    private boolean enabled;
    private AuthorityDTO authorityDTO;
}
