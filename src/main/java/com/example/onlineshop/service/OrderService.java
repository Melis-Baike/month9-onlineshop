package com.example.onlineshop.service;

import com.example.onlineshop.DTO.OrderDTO;
import com.example.onlineshop.DTO.UserDTO;
import com.example.onlineshop.entity.BasketProduct;
import com.example.onlineshop.entity.Order;
import com.example.onlineshop.entity.User;
import com.example.onlineshop.repository.BasketProductRepository;
import com.example.onlineshop.repository.OrderRepository;
import com.example.onlineshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final BasketProductRepository basketProductRepository;
    private final UserRepository userRepository;

    public OrderDTO checkout(HttpSession session, Long basketProductId){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        User user = userRepository.findByEmail(userDTO.getEmail()).get();
        BasketProduct basketProduct = basketProductRepository.findById(basketProductId).get();
        Order order = Order.builder()
                .user(user)
                .productName(basketProduct.getProductName())
                .productQuantity(basketProduct.getProductQuantity())
                .productPrice(basketProduct.getProductPrice())
                .totalPrice(basketProduct.getProductQuantity() * basketProduct.getProductPrice())
                .time(LocalDateTime.now())
                .build();
        orderRepository.save(order);
        basketProductRepository.delete(basketProduct);
        return OrderDTO.from(order);
    }
}
