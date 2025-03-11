package com.example.OAuth_Demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HomeController {

    @Autowired
    OAuth2AuthorizedClientService clientService;

    @GetMapping("/")
    public String home() {
        return "Hello Home";
    }

    @GetMapping("/secured")
    public String secured() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2User user = (OAuth2User) authentication.getPrincipal();   //this user has some attributes stored in it.Some of them are login, company.You can view them when you debug this line.

        String login = user.getAttribute("login") != null ? user.getAttribute("login") : "Guest";
        String company = user.getAttribute("company") != null ? user.getAttribute("company") : "Unknown Company";

        return "Hello, ".concat(login).concat(" from ").concat(company);
    }


}
