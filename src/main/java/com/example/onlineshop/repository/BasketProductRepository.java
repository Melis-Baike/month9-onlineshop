package com.example.onlineshop.repository;

import com.example.onlineshop.entity.BasketProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface BasketProductRepository extends JpaRepository<BasketProduct, Long> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE BasketProduct d SET d.productQuantity = :quantity WHERE d.id = :id")
    void setProductQuantityById(@Param("quantity") Long quantity, @Param("id") Long id);
}
