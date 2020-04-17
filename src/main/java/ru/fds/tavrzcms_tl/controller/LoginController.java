package ru.fds.tavrzcms_tl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    private static final String PAGE_LOGIN = "login";

    @GetMapping("/")
    public String login() {
        return PAGE_LOGIN;
    }
}
