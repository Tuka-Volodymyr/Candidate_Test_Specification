package com.example.candidate_test_specification.web.controllers.product;

import java.time.LocalDate;

public record ProductResponse(
         LocalDate entryDate,
         String itemCode,
         String itemName,
         int itemQuantity,
         String status
) { }
