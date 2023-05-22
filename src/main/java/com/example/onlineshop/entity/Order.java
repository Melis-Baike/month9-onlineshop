package com.example.onlineshop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    private User user;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_quantity")
    private Long productQuantity;
    @Column(name = "product_price")
    private Double productPrice;
    @Column(name = "total_price")
    private Double totalPrice;
    @Column(name = "time")
    private LocalDateTime time;
}
