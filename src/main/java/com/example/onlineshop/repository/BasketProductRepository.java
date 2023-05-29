package com.example.onlineshop.repository;

import com.example.onlineshop.entity.Basket;
import com.example.onlineshop.entity.BasketProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface BasketProductRepository extends JpaRepository<BasketProduct, Long> {
    Optional<List<BasketProduct>> findAllByBasket(Basket basket);
    @Modifying
    @Transactional
    @Query(value = "UPDATE BasketProduct d SET d.productQuantity = :quantity WHERE d.id = :id")
    void setProductQuantityById(@Param("quantity") Long quantity, @Param("id") Long id);
}
