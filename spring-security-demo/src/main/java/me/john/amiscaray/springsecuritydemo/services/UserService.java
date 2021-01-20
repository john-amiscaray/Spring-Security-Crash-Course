package me.john.amiscaray.springsecuritydemo.services;

import me.john.amiscaray.springsecuritydemo.data.UserRepo;
import me.john.amiscaray.springsecuritydemo.dtos.UserDto;
import me.john.amiscaray.springsecuritydemo.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder){

        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;

    }

    public void saveUser(UserDto dto){

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepo.save(new User(dto));

    }

    @PreAuthorize("#username == authentication.principal.username")
    public String getSecret(String username){

        User user = userRepo.findUserByUsername(username).orElseThrow();
        return user.getSecret();

    }

}
