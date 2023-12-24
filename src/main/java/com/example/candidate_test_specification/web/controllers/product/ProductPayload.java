package com.example.candidate_test_specification.web.controllers.product;

import com.example.candidate_test_specification.domain.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPayload {
    @NotBlank(message = "Table should not be empty!")
    private String table;
    @NotEmpty(message = "Records should not be empty!")
    @Valid
    private List<ProductDto> records;
}
