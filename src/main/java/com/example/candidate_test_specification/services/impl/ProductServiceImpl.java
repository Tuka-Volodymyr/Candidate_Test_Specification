package com.example.candidate_test_specification.services.impl;

import com.example.candidate_test_specification.domain.Product;
import com.example.candidate_test_specification.domain.exceptions.BadRequestException;
import com.example.candidate_test_specification.repository.ProductRepository;
import com.example.candidate_test_specification.services.ProductService;
import com.example.candidate_test_specification.services.UserService;
import com.example.candidate_test_specification.web.controllers.product.ProductDto;
import com.example.candidate_test_specification.web.controllers.product.ProductPayload;
import com.example.candidate_test_specification.web.controllers.product.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserService userService;
    public static final Sort sort = Sort.by(Sort.Direction.DESC, "entryDate");

    @Override
    public void save(Product product) {
        if (product != null) {
            productRepository.save(product);
        } else {
            throw new NullPointerException();
        }
    }
    @SneakyThrows
    @Override
    @Transactional
    public void addProduct(ProductPayload productPayload) {
        for(ProductDto productDto:productPayload.getRecords()){
            Product product=Product.builder()
                    .entryDate(toLocalDate(productDto.getEntryDate()))
                    .itemCode(productDto.getItemCode())
                    .itemName(productDto.getItemName())
                    .itemQuantity(productDto.getItemQuantity())
                    .status(productDto.getStatus())
                    .user(userService.getUser())
                    .build();
            save(product);
        }
    }
    @Override
    public LocalDate toLocalDate(String entryDate){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return LocalDate.parse(entryDate, formatter);
        } catch (DateTimeParseException e) {
            throw new BadRequestException("Data is wrong");
        }
    }

    @Override
    public List<ProductResponse> getAllProduct() {
        return Product.toProductResponseList(productRepository.findAll(sort));
    }
}
