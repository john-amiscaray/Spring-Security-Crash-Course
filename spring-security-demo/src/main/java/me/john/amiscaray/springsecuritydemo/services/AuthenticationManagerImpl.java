package me.john.amiscaray.springsecuritydemo.services;

import me.john.amiscaray.springsecuritydemo.data.UserRepo;
import me.john.amiscaray.springsecuritydemo.entities.User;
import me.john.amiscaray.springsecuritydemo.exception.AuthenticationExceptionImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationManagerImpl implements AuthenticationManager {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationManagerImpl(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        /*
         We will have the principal and credentials fields be used as usernames and passwords respectively, so we
         need to assert that they are indeed Strings. This will throw an Exception if that is not true.
         */
        assert (authentication.getPrincipal() instanceof String && authentication.getCredentials() instanceof String);

        User user = userRepo.findUserByUsername((String) authentication.getPrincipal()).orElseThrow();

        if(passwordEncoder.matches((String) authentication.getCredentials(), user.getPassword())){

            return authentication;

        }

        /*
         AuthenticationExceptionImpl is a simple class I defined which extends AuthenticationException (an abstract class).
         */
        throw new AuthenticationExceptionImpl("Could not verify user");

    }
}
