package com.example.onlineshop.DTO;

import com.example.onlineshop.entity.Role;
import com.example.onlineshop.entity.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDTO {
    public static UserDTO from(User user){
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .enabled(user.isEnabled())
                .roles(user.getRoles())
                .build();
    }

    private Long id;
    private String name;
    private String email;
    private boolean enabled;
    private List<Role> roles;
}
