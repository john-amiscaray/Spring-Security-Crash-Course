package me.john.amiscaray.springsecuritydemo.services;

import me.john.amiscaray.springsecuritydemo.data.UserRepo;
import me.john.amiscaray.springsecuritydemo.entities.AppUserDetails;
import me.john.amiscaray.springsecuritydemo.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findUserByUsername(s);
        if(user.isPresent()){

            return new AppUserDetails(user.get());

        }else{

            throw new UsernameNotFoundException("User not found");

        }
    }
}
