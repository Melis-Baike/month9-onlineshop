package com.example.onlineshop.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserLoginDTO {
    @Email(message = "Enter your email")
    @NotBlank(message = "The email can't be empty")
    private String email;
    @NotBlank(message = "The password can't be empty")
    @Size(min = 4, message = "The password must have at least 4 characters")
    private String password;
}
