package com.example.onlineshop.repository;

import com.example.onlineshop.entity.Product;
import com.example.onlineshop.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByProduct(Product product, Pageable pageable);
}
