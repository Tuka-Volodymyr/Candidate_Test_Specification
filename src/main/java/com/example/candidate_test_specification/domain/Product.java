package com.example.candidate_test_specification.domain;

import com.example.candidate_test_specification.web.controllers.product.ProductResponse;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_id;
    private LocalDate entryDate;
    private String itemCode;
    private String itemName;
    private int itemQuantity;
    private String status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public static List<ProductResponse> toProductResponseList(List<Product> productList) {
        return productList.stream()
                .map(prod -> new ProductResponse(
                        prod.getEntryDate(),
                        prod.getItemCode(),
                        prod.getItemName(),
                        prod.getItemQuantity(),
                        prod.getStatus()
                ))
                .collect(Collectors.toList());
    }
}
