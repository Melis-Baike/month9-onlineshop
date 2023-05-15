package com.example.onlineshop.controller;

import com.example.onlineshop.DTO.BasketProductDTO;
import com.example.onlineshop.DTO.ChangeProductQuantityDTO;
import com.example.onlineshop.DTO.OrderProductDTO;
import com.example.onlineshop.service.BasketProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/basket")
public class BasketController {
    private final BasketProductService basketProductService;

    @PostMapping("/ordering")
    public ResponseEntity<BasketProductDTO> putProductInBasket(Authentication authentication,
                                                        @Valid @RequestBody OrderProductDTO orderProductDTO){
        BasketProductDTO basketProductDTO = basketProductService.putInTheBasket(authentication, orderProductDTO.getName(),
                orderProductDTO.getQuantity());
        if(basketProductDTO == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(basketProductDTO, HttpStatus.OK);
        }
    }

    @PostMapping("/quantity")
    public ResponseEntity<BasketProductDTO> changeProductQuantity(@Valid @RequestBody ChangeProductQuantityDTO changeProductQuantityDTO){
        BasketProductDTO basketProductDTO = basketProductService.changeQuantityOfProduct(
                changeProductQuantityDTO.getProductName(), changeProductQuantityDTO.getBasketProductId(),
                changeProductQuantityDTO.getPreviousQuantity(), changeProductQuantityDTO.getCurrentQuantity());
        if(basketProductDTO == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(basketProductDTO, HttpStatus.OK);
        }
    }

    @PostMapping("/{basketProductId}")
    public ResponseEntity<String> removeProductFromBasket(HttpServletRequest request, Authentication authentication,
                                                          @PathVariable @Valid @Min(value = 1) @NotNull Long basketProductId){
        String methodOverride = request.getHeader("X-HTTP-Method-Override");
        if("DELETE".equals(methodOverride)) {
            return new ResponseEntity<>(basketProductService.deleteBasketProductElement(authentication, basketProductId),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}
