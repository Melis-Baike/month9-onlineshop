package com.example.onlineshop.repository;

import com.example.onlineshop.entity.BasketProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BasketProductRepository extends JpaRepository<BasketProduct, Long> {
}
