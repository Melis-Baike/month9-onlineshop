package com.example.onlineshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequestMapping("/captcha")
public class CaptchaController {
    private static String CAPTCHA;
    @GetMapping()
    public String captcha(Model model){
        String captcha = UUID.randomUUID().toString();
        CAPTCHA = captcha;
        model.addAttribute("captcha", captcha);
        return "captcha";
    }

    @PostMapping()
    public String submitForm(@RequestParam("text") String captcha) {
        if(CAPTCHA.equals(captcha)){
            return "register";
        } else {
            return "redirect:/captcha";
        }
    }
}

