package com.example.onlineshop.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderProductDTO {
    @NotBlank
    private String name;
    @Min(value = 1, message = "Quantity must be positive")
    @NotNull
    private Long quantity;

    public void setQuantity(String quantity) {
        this.quantity = Long.parseLong(quantity);
    }
}
