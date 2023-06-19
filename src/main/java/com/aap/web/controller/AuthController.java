package com.aap.web.controller;

import com.aap.web.dto.RegistrationDto;
import com.aap.web.models.UserEntity;
import com.aap.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user") RegistrationDto user,
                           BindingResult result, Model model){
        UserEntity existingUsersEmail = userService.findByEmail(user.getEmail());
        if(existingUsersEmail != null && existingUsersEmail.getEmail() != null && !existingUsersEmail.getEmail().isEmpty()){
            return "redirect:/register?fail";
        }
        UserEntity existingUsername = userService.findByUserName(user.getUsername());
        if(existingUsername != null && existingUsername.getUsername() != null && !existingUsername.getUsername().isEmpty()){
            return "redirect:/register?fail";
        }
        if(result.hasErrors()){
            model.addAttribute("user. user");
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/clubs?success";

    }
}
