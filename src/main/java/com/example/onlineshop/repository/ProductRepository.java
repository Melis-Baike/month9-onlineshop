package com.example.onlineshop.repository;

import com.example.onlineshop.entity.Category;
import com.example.onlineshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    Page<Product> findAllByName(String name, Pageable pageable);
    Page<Product> findAllByPrice(Double price, Pageable pageable);
    Page<Product> findAllByCategory(Category category, Pageable pageable);

    Page<Product> findAllByNameAndPriceAndCategory(String name, Double price, Category category, Pageable pageable);

    Page<Product> findAllByNameAndPrice(String name, Double price, Pageable pageable);
    Page<Product> findAllByNameAndCategory(String name, Category category, Pageable pageable);
    Page<Product> findAllByPriceAndCategory(Double price, Category category, Pageable pageable);
}
