package com.example.onlineshop.service;

import com.example.onlineshop.DTO.BasketProductDTO;
import com.example.onlineshop.DTO.CustomOrderDTO;
import com.example.onlineshop.DTO.OrderDTO;
import com.example.onlineshop.DTO.UserDTO;
import com.example.onlineshop.entity.BasketProduct;
import com.example.onlineshop.entity.Order;
import com.example.onlineshop.entity.User;
import com.example.onlineshop.repository.BasketProductRepository;
import com.example.onlineshop.repository.OrderRepository;
import com.example.onlineshop.repository.ProductRepository;
import com.example.onlineshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final BasketProductRepository basketProductRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

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
        List<BasketProductDTO> list = (List<BasketProductDTO>) session.getAttribute("basket");
        list.removeIf(e -> Objects.equals(e.getId(), basketProductId));
        session.setAttribute("basket", list);
        return OrderDTO.from(order);
    }

    public boolean check(HttpSession session, String productName){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        User user = userRepository.findByEmail(userDTO.getEmail()).get();
        Optional<List<Order>> optionalOrder = orderRepository.findByUserAndProductName(user, productName);
        return optionalOrder.get().size() != 0;
    }

    public List<CustomOrderDTO> getMyOrders(HttpSession session){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        User user = userRepository.findByEmail(userDTO.getEmail()).get();
        return orderRepository.findByUser(user).get().stream().map(e -> CustomOrderDTO.from(e,
                productRepository.findByName(e.getProductName()).get().getImage())).collect(Collectors.toList());
    }
}
