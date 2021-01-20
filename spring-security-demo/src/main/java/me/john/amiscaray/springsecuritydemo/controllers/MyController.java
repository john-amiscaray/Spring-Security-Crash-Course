package me.john.amiscaray.springsecuritydemo.controllers;

import me.john.amiscaray.springsecuritydemo.dtos.UserDto;
import me.john.amiscaray.springsecuritydemo.services.JWTAuthService;
import me.john.amiscaray.springsecuritydemo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MyController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTAuthService authService;

    @PostMapping("/api/auth/signup")
    public ResponseEntity<Void> signUp(@RequestBody UserDto userDto){

        userService.saveUser(userDto);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/api/hello-world")
    public String helloWorld(){

        return "Hello World";

    }


    @GetMapping("/api/secret-admin-business")
    public Integer getMeaningOfLife(){

        return 42;

    }

    @PostMapping("/api/auth/login")
    public String JWTLogin(@RequestBody UserDto userDto){

        return authService.getJWT(userDto);

    }

    @GetMapping("/api/user/{username}/secret")
    public String getSecret(@PathVariable("username") String secret){

        return userService.getSecret(secret);

    }


}
