package com.example.onlineshop.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegistrationDTO {
    private String name;
    private String email;
    private String password;
}
