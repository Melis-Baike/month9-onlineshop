package com.example.onlineshop.DTO;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GuestDTO {
    public static GuestDTO from(String email, String password){
        return GuestDTO.builder()
                .email(email)
                .password(password)
                .build();
    }

    private String email;
    private String password;
}
