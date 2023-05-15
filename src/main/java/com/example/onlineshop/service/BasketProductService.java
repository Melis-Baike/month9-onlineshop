package com.example.onlineshop.service;

import com.example.onlineshop.DTO.BasketProductDTO;
import com.example.onlineshop.entity.Basket;
import com.example.onlineshop.entity.BasketProduct;
import com.example.onlineshop.entity.Product;
import com.example.onlineshop.entity.User;
import com.example.onlineshop.repository.BasketProductRepository;
import com.example.onlineshop.repository.BasketRepository;
import com.example.onlineshop.repository.ProductRepository;
import com.example.onlineshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasketProductService {
    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final BasketProductRepository basketProductRepository;

    public BasketProductDTO putInTheBasket(Authentication authentication, String productName, Long quantity){
        Product product = productRepository.findByName(productName).get();
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
            if(authentication != null && !authentication.getName().isBlank()) {
                User user = userRepository.findByEmail(authentication.getName()).get();
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

    public String deleteBasketProductElement(Authentication authentication, Long basketProductId){
        if(authentication != null && !authentication.getName().isBlank()) {
            User user = userRepository.findByEmail(authentication.getName()).get();
            BasketProduct basketProduct = basketProductRepository.findById(basketProductId).get();
            if (basketProduct.getBasket().getUser().equals(user)) {
                basketProductRepository.deleteById(basketProductId);
            }
        }
        basketProductRepository.deleteById(basketProductId);
        return "You have successfully removed product";
    }
}
