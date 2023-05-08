package com.example.onlineshop.repository;

import com.example.onlineshop.entity.Basket;
import com.example.onlineshop.entity.Product;
import com.example.onlineshop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
    Page<Basket> findAllByUser(User user, Pageable pageable);
}
