package me.john.amiscaray.oauthdemo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String getWelcomeMessage(){

        return "Hello User!";

    }

}
