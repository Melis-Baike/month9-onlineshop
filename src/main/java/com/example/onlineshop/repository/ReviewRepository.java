package com.example.onlineshop.repository;

import com.example.onlineshop.entity.Product;
import com.example.onlineshop.entity.Review;
import com.example.onlineshop.entity.User;
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
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByProduct(Product product, Pageable pageable);

    Optional<Review> findByUserAndProduct(User user, Product product);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Review r SET r.text = :text, r.rating = :rating WHERE r.id = :id")
    void setTextAndRatingById(@Param("text") String quantity,@Param("rating") Double rating,  @Param("id") Long id);
}
