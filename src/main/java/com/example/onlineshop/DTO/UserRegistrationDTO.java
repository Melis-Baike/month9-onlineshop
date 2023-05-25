package com.example.onlineshop.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserRegistrationDTO {
    @NotBlank(message = "The name can't be empty")
    @Size(min=4, max=24, message = "Length must be >= 4 and <= 24")
    @Pattern(regexp = "^[^\\d\\s]+$", message = "Should contain only letters")
    private String name;
    @Email(message = "Enter your email")
    @NotBlank(message = "The email can't be empty")
    private String email;
    @NotBlank(message = "The password can't be empty")
    @Size(min = 4, message = "The password must have at least 4 characters")
    private String password;
}
