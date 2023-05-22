package com.example.onlineshop.service;

import com.example.onlineshop.DTO.BasketProductDTO;
import com.example.onlineshop.DTO.UserDTO;
import com.example.onlineshop.entity.Basket;
import com.example.onlineshop.entity.BasketProduct;
import com.example.onlineshop.entity.Product;
import com.example.onlineshop.entity.User;
import com.example.onlineshop.repository.BasketProductRepository;
import com.example.onlineshop.repository.BasketRepository;
import com.example.onlineshop.repository.ProductRepository;
import com.example.onlineshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasketProductService {
    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final BasketProductRepository basketProductRepository;

    public BasketProductDTO putInTheBasket(UserDTO userDTO, String productName, Long quantity){
        Product product = productRepository.findByName(productName).get();
        User user = userRepository.findByEmail(userDTO.getEmail()).get();
        if(product.getQuantity() > quantity){
            product.setQuantity(product.getQuantity() - quantity);
            productRepository.setQuantityById(product.getQuantity(), product.getId());
            String name = product.getName();
            String image = product.getImage();
            String description = product.getDescription();
            BasketProduct basketProduct = BasketProduct.builder()
                    .productName(name)
                    .productImage(image)
                    .productQuantity(quantity)
                    .productDescription(description)
                    .productPrice(product.getPrice())
                    .category(product.getCategory())
                    .brand(product.getBrand())
                    .build();
                Optional<Basket> optionalBasket = basketRepository.findByUser(user);
                if (optionalBasket.isPresent()) {
                    basketProduct.setBasket(optionalBasket.get());
                } else {
                    Basket basket = Basket.builder()
                            .user(user)
                            .build();
                    basketRepository.save(basket);
                    basketProduct.setBasket(basket);
                }
            basketProductRepository.save(basketProduct);
            return BasketProductDTO.from(basketProduct);
        } else {
            return null;
        }
    }

    public BasketProductDTO changeQuantityOfProduct(String productName, Long basketProductId,
                                                    Long previousQuantity, Long currentQuantity){
        Product product = productRepository.findByName(productName).get();
        BasketProduct basketProduct = basketProductRepository.findById(basketProductId).get();
        long diff = currentQuantity - previousQuantity;
        if(product.getQuantity() > currentQuantity){
            product.setQuantity(product.getQuantity() - (diff));
            productRepository.setQuantityById(product.getQuantity(), product.getId());
            basketProduct.setProductQuantity(currentQuantity);
            basketProductRepository.setProductQuantityById(basketProduct.getProductQuantity(), basketProduct.getId());
            return BasketProductDTO.from(basketProduct);
        } else {
            return null;
        }
    }

    public String deleteBasketProductElement(UserDTO userDTO, Long basketProductId){
        if(userDTO != null && !userDTO.getName().isBlank()) {
            User user = userRepository.findByEmail(userDTO.getEmail()).get();
            BasketProduct basketProduct = basketProductRepository.findById(basketProductId).get();
            if (basketProduct.getBasket().getUser().equals(user)) {
                Long quantity = basketProduct.getProductQuantity();
                System.out.println("Quantity: " + quantity);
                Product product = productRepository.findByName(basketProduct.getProductName()).get();
                System.out.println(product.getQuantity());
                product.setQuantity(product.getQuantity() + quantity);
                System.out.println(product.getQuantity());
                productRepository.setQuantityById(product.getQuantity(), product.getId());
                basketProductRepository.deleteById(basketProductId);
                return "You have successfully removed product";
            }
        }
        return "Something went wrong!";
    }

    public Optional<List<BasketProductDTO>> getUserBasket(String email){
        Optional<Basket> basket = basketRepository.findByUser(userRepository.findByEmail(email).get());
        return basket.map(value -> basketProductRepository.findAllByBasket(value).get().stream()
                .map(BasketProductDTO::from).collect(Collectors.toList()));
    }
}
