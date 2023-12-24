package com.example.candidate_test_specification.web.controllers;

import com.example.candidate_test_specification.services.ProductService;
import com.example.candidate_test_specification.web.controllers.product.ProductDto;
import com.example.candidate_test_specification.web.controllers.product.ProductPayload;
import com.example.candidate_test_specification.web.controllers.product.ProductResponse;
import com.example.candidate_test_specification.web.controllers.user.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody @Valid ProductPayload productPayload) {
        productService.addProduct(productPayload);
        return new ResponseEntity<>("Product(s) was added.", HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProduct(), HttpStatus.OK);
    }

}
