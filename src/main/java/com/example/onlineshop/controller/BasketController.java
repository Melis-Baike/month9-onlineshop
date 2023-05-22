package com.example.onlineshop.controller;

import com.example.onlineshop.DTO.BasketProductDTO;
import com.example.onlineshop.DTO.ChangeProductQuantityDTO;
import com.example.onlineshop.DTO.OrderProductDTO;
import com.example.onlineshop.DTO.UserDTO;
import com.example.onlineshop.service.BasketProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/basket")
public class BasketController {
    private final BasketProductService basketProductService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/ordering")
    public ResponseEntity<BasketProductDTO> putProductInBasket(HttpSession session,
                                                        @Valid @RequestBody OrderProductDTO orderProductDTO){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if(userDTO != null) {
            BasketProductDTO basketProductDTO = basketProductService.putInTheBasket(userDTO, orderProductDTO.getName(),
                    orderProductDTO.getQuantity());
            if (basketProductDTO == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            } else {
                Object obj = session.getAttribute("basket");
                List<BasketProductDTO> list;
                if(obj == null){
                    list = new ArrayList<>();
                } else {
                    list = (List<BasketProductDTO>) obj;
                }
                list.add(basketProductDTO);
                session.setAttribute("basket", list);
                return new ResponseEntity<>(basketProductDTO, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/quantity")
    public ResponseEntity<BasketProductDTO> changeProductQuantity(@Valid @RequestBody ChangeProductQuantityDTO changeProductQuantityDTO,
                                                                  HttpSession session){
        BasketProductDTO basketProductDTO = basketProductService.changeQuantityOfProduct(
                changeProductQuantityDTO.getProductName(), changeProductQuantityDTO.getBasketProductId(),
                changeProductQuantityDTO.getPreviousQuantity(), changeProductQuantityDTO.getCurrentQuantity());
        if(basketProductDTO == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            Object obj = session.getAttribute("basket");
            List<BasketProductDTO> list;
            if(obj == null){
                list = new ArrayList<>();
            } else {
                list = (List<BasketProductDTO>) obj;
            }
            list.stream().filter(e -> e.getId() == basketProductDTO.getId()).findFirst().get()
                    .setProductQuantity(basketProductDTO.getProductQuantity());
            session.setAttribute("basket", list);
            return new ResponseEntity<>(basketProductDTO, HttpStatus.OK);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{basketProductId}")
    public ResponseEntity<String> removeProductFromBasket(HttpServletRequest request, HttpSession session,
                                                          @PathVariable @Valid @Min(value = 1) @NotNull Long basketProductId){
        String methodOverride = request.getHeader("X-HTTP-Method-Override");
        if("DELETE".equals(methodOverride)) {
            Object obj = session.getAttribute("basket");
            List<BasketProductDTO> list;
            if(obj == null){
                list = new ArrayList<>();
            } else {
                list = (List<BasketProductDTO>) obj;
                list.removeIf(e -> Objects.equals(e.getId(), basketProductId));
            }
            session.setAttribute("basket", list);
            UserDTO userDTO = (UserDTO) session.getAttribute("user");
            return new ResponseEntity<>(basketProductService.deleteBasketProductElement(userDTO, basketProductId),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}
