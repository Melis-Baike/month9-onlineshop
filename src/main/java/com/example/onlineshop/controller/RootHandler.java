package com.example.onlineshop.controller;

import com.example.onlineshop.DTO.BasketProductDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/")
public class RootHandler {
    @GetMapping()
    public String home() {
        return "main";
    }

    @GetMapping("basket")
    public String basket(HttpSession session, Model model) {
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
}
