package com.example.onlineshop.repository;

import com.example.onlineshop.entity.Order;
import com.example.onlineshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<List<Order>> findByUserAndProductName(User user, String productName);
    Optional<List<Order>> findByUser(User user);
}
