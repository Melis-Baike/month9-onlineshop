package com.example.onlineshop.controller;

import com.example.onlineshop.DTO.BasketProductDTO;
import com.example.onlineshop.DTO.PasswordRecoveryDTO;
import com.example.onlineshop.DTO.UserDTO;
import com.example.onlineshop.DTO.UserRegistrationDTO;
import com.example.onlineshop.entity.User;
import com.example.onlineshop.service.BasketProductService;
import com.example.onlineshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login?logout";
    }

    @GetMapping("/specifyEmail")
    public String getSpecifyEmailPage(){
        return "specifyEmail";
    }

    @PostMapping("/specifyEmail")
    public String setEmailForPasswordRecovery(@RequestParam("text") String email, Model model){
        Optional<User> optionalUser = userService.findByEmail(email);
        if(optionalUser.isPresent()){
            String token = UUID.randomUUID().toString();
            model.addAttribute("token", token);
            model.addAttribute("email", email);
            return "confirmToken";
        }
        return "redirect:/users/specifyEmail";
    }

    @PostMapping("/token")
    public String getTokenPage(@RequestParam("email") String email, @RequestParam("token") String token,
                               @RequestParam("text") String confirmedToken, Model model){
        Optional<User> optionalUser = userService.findByEmail(email);
        if(optionalUser.isPresent()){
            model.addAttribute("email", email);
            if(token.equals(confirmedToken)) {
                return "passwordRecovery";
            } else {
                String newToken = UUID.randomUUID().toString();
                model.addAttribute("token", newToken);
                return "confirmToken";
            }
        } else {
            return "redirect:/users/specifyEmail";
        }
    }

    @PostMapping("/passwordRecovery")
    public String recoveryPassword(@Valid PasswordRecoveryDTO passwordRecoveryDTO){
        boolean bool = userService.recoveryPassword(passwordRecoveryDTO);
        if(bool){
            return "successfulPasswordRecovery";
        } else {
            return "unsuccessfulPasswordRecovery";
        }
    }
}
