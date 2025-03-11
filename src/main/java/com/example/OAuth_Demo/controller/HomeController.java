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
    OAuth2AuthorizedClientService clientService;    //this was not a class created by us.

    @GetMapping("/")
    public String home() {
        return "Hello Home";
    }


    @GetMapping("/secured")
    public String secured() {
        //to fetch user details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //to fetch authentication token shared by github
        OAuth2AuthenticationToken oauthToken=(OAuth2AuthenticationToken) authentication;

        OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());
        String accessToken = client.getAccessToken().getTokenValue();   //fetches accesstoken

        log.info("user:"+ authentication.getPrincipal());
        log.info("access token:"+ accessToken);

        OAuth2User user = (OAuth2User) authentication.getPrincipal();   //to get logged in user details
        String login = user.getAttribute("login") != null ? user.getAttribute("login") : "Guest";
        String company = user.getAttribute("company") != null ? user.getAttribute("company") : "Unknown Company";

        return "Hello, ".concat(login).concat(" from ").concat(company);
        //        return "Hello Secured";
    }






}
