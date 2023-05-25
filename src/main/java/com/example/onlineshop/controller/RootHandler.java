package com.example.onlineshop.controller;

import com.example.onlineshop.DTO.BasketProductDTO;
import com.example.onlineshop.DTO.CustomOrderDTO;
import com.example.onlineshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class RootHandler {
    private final OrderService orderService;
    @GetMapping()
    public String home() {
        return "main";
    }

    @GetMapping("basket")
    public String getBasket(HttpSession session, Model model) {
        Object obj = session.getAttribute("basket");
        List<BasketProductDTO> list;
        if(obj == null){
            list = new ArrayList<>();
        } else {
            list = (List<BasketProductDTO>) obj;
        }
        model.addAttribute("products", list);
        return "basket";
    }

    @GetMapping("myOrders")
    public String getMyOrders(HttpSession session, Model model){
        List<CustomOrderDTO> orders = orderService.getMyOrders(session);
        model.addAttribute("products", orders);
        return "myOrders";
    }
}
