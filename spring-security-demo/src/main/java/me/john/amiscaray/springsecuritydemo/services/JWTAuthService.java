package me.john.amiscaray.springsecuritydemo.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import me.john.amiscaray.springsecuritydemo.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTAuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final String SECRET = "secret";

    @Autowired
    public JWTAuthService(AuthenticationManager authenticationManager,
                          @Qualifier("appUserDetailsService") UserDetailsService userDetailsService){

        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;

    }

    public String getJWT(UserDto dto){

        try {
            UserDetails user = userDetailsService.loadUserByUsername(dto.getUsername());
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    dto.getUsername(),
                    dto.getPassword(),
                    user.getAuthorities()

            ));
        }catch (AuthenticationException ex){

            throw new IllegalArgumentException("User not found");

        }

        long TEN_HOURS = 36000000L;
        return JWT.create()
                .withSubject(dto.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + TEN_HOURS))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));

    }

    public UsernamePasswordAuthenticationToken verify(String token){
        // Decode the token, verify it and get the subject
        String username = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token)
                .getSubject();
        // If username is not null, get the UserDetails and return a new UsernamePasswordAuthenticationToken
        if(username != null){

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());

        }
        return null;

    }



}
