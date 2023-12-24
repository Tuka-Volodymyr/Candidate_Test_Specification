package com.example.candidate_test_specification.web.controllers.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    @NotBlank(message = "EntryDate should not be empty!")
    private String entryDate;
    @NotBlank(message = "ItemCode should not be empty!")
    private String itemCode;
    @NotBlank(message = "ItemName should not be empty!")
    private String itemName;
    @Min(value = 1, message = "ItemQuantity should not be empty and should be greater than 0!")
    private int itemQuantity;
    @NotBlank(message = "Status should not be empty!")
    private String status;
}
