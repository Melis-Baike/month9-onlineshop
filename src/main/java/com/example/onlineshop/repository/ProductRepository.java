package com.example.onlineshop.repository;

import com.example.onlineshop.entity.Category;
import com.example.onlineshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Modifying
    @Transactional
    @Query(value = "UPDATE Product d SET d.quantity = :quantity WHERE d.id = :id")
    void setQuantityById(@Param("quantity") Long quantity, @Param("id") Long id);
}
