package com.isw.mb.fantacalcio.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {


    @GetMapping("/")
    public String showLoginForm() {
        return "loginView";
    }


    @GetMapping("/register")
    public String showRegisterForm() {
        return "registerView";
    }


}
