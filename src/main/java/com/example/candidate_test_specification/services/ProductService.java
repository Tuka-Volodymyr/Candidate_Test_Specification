package com.example.candidate_test_specification.services;

import com.example.candidate_test_specification.domain.Product;
import com.example.candidate_test_specification.domain.User;
import com.example.candidate_test_specification.web.controllers.product.ProductPayload;
import com.example.candidate_test_specification.web.controllers.product.ProductResponse;

import java.time.LocalDate;
import java.util.List;

public interface ProductService {
    void addProduct(ProductPayload productPayload);

    LocalDate toLocalDate(String entryDate);

    List<ProductResponse> getAllProduct();
    void save(Product product);

}
