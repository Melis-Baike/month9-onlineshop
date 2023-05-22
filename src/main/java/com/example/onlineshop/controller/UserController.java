package com.example.onlineshop.controller;

import com.example.onlineshop.DTO.BasketProductDTO;
import com.example.onlineshop.DTO.UserDTO;
import com.example.onlineshop.DTO.UserRegistrationDTO;
import com.example.onlineshop.service.BasketProductService;
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
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final BasketProductService basketProductService;

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
        return "redirect:/captcha";
    }


    @GetMapping("/auth")
    public String auth(HttpSession session){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal() != "anonymousUser") {
            UserDTO userDTO = userService.login(authentication);
            session.setAttribute("user", userDTO);
            Optional<List<BasketProductDTO>> basketProductDTOList = basketProductService.getUserBasket(userDTO.getEmail());
            basketProductDTOList.ifPresent(basketProductDTOS -> session.setAttribute("basket", basketProductDTOS));
            System.out.println(session.getAttribute("user"));
            System.out.println(session.getAttribute("basket"));
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
    public String logout(HttpServletRequest request, HttpSession session, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        session.removeAttribute("user");
        session.removeAttribute("basket");
        return "redirect:/login?logout";
    }
}
