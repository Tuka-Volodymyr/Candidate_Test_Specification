package com.example.candidate_test_specification.repository;

import com.example.candidate_test_specification.domain.Product;
import com.example.candidate_test_specification.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
