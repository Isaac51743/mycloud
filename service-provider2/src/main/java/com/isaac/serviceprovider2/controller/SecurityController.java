package com.isaac.serviceprovider2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class SecurityController {

    @PostMapping("/tomain")
    public String toMain() {
        System.out.println("redirect to main page");
        return "redirect:main.html";
    }

    @PostMapping("/toerror")
    public String toError() {
        System.out.println("redirect to error page");
        return "redirect:error.html";
    }

//    @RequestMapping("/tologin")
//    public String toLogin() {
//        System.out.println("logged in with csrf");
//        return "/csrflogin.jsp";
//    }

}
