package me.john.amiscaray.springsecuritydemo.filter;

import me.john.amiscaray.springsecuritydemo.services.JWTAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTFilter extends BasicAuthenticationFilter {

    private final JWTAuthService authService;

    public JWTFilter(AuthenticationManager authenticationManager, JWTAuthService authService){
        super(authenticationManager);
        this.authService = authService;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        // Get Authorization header
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        // Remove the "Bearer" prefix
        String token = authorizationHeader.substring(7);
        // Verify token
        UsernamePasswordAuthenticationToken auth = authService.verify(token);

        SecurityContextHolder.getContext().setAuthentication(auth);
        // send request through next filter
        filterChain.doFilter(httpServletRequest, httpServletResponse);


    }
}
