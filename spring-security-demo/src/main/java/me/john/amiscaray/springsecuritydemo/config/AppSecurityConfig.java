package me.john.amiscaray.springsecuritydemo.config;

import me.john.amiscaray.springsecuritydemo.filter.JWTFilter;
import me.john.amiscaray.springsecuritydemo.services.AppUserDetailsService;
import me.john.amiscaray.springsecuritydemo.services.JWTAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JWTAuthService authService;

    @Autowired
    private AppUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // HTTP Basic config
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/api/secret-admin-business").hasAnyRole("ADMIN")
//                .anyRequest().authenticated()
//                .anyRequest().authenticated()
//                .and().httpBasic();
        // JWT config
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/secret-admin-business").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTFilter(authenticationManager, authService))
                // Remove sessions since we are now using JWT
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/api/auth/signup")
                .antMatchers("/api/auth/login");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){

        return new BCryptPasswordEncoder(10);

    }
}
