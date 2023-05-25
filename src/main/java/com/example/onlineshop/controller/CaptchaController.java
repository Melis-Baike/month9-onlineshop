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
    @GetMapping()
    public String captcha(Model model){
        model.addAttribute("captcha", UUID.randomUUID().toString());
        return "captcha";
    }

    @PostMapping()
    public String submitForm(@RequestParam("captcha") String serverCaptcha, @RequestParam("text") String captcha) {
        if(serverCaptcha.equals(captcha)){
            return "register";
        } else {
            return "redirect:/captcha";
        }
    }
}

