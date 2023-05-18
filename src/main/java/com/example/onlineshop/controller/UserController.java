package com.example.onlineshop.controller;

import com.example.onlineshop.DTO.UserRegistrationDTO;
import com.example.onlineshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register/post")
    public String registerPage(@ModelAttribute @Valid UserRegistrationDTO userRegistrationDTO, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            return "unsuccessfulRegEntry";
        }
        String response = userService.register(userRegistrationDTO);
        if(response.equals("Enter another email")){
            return "unsuccessfulRegEntry";
        }
        return "successfulRegEntry";
    }

    @GetMapping("/register")
    public String pageRegisterCustomer() {
        return "register";
    }


    @GetMapping("/auth")
    public String auth(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal() != "anonymousUser") {
            userService.login(authentication);
            return "redirect:/";
        } else {
            return "login";
        }
    }


    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/unsuccessfulLoginEntry")
    public String showPostUnsuccessfulLogin() {
        return "unsuccessfulLoginEntry";
    }

    @GetMapping("/unsuccessfulLoginEntry")
    public String showGetUnsuccessfulLogin() {
        return "unsuccessfulLoginEntry";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login?logout";
    }
}
