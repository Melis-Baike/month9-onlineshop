package com.example.onlineshop.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FeedbackDTO {
    @NotBlank
    @Size(min=1, message = "Length must be higher than 0")
    private String productName;
    @NotNull(message = "Rating can't be empty")
    @Min(value = 0, message = "Rating can't be less than 0")
    @Max(value = 5, message = "Rating can't be higher than 5")
    private Double rating;
    @NotBlank(message = "The feedback can't be empty")
    @Size(min=1, message = "Length must be higher than 0")
    private String text;
}
