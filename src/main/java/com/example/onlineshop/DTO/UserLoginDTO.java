package com.example.onlineshop.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {
    @Email(message = "Enter your email")
    @NotBlank(message = "The email can't be empty")
    private String email;
    @NotBlank(message = "The password can't be empty")
    @Size(min = 4, message = "The password must have at least 8 characters")
    private String password;
}
