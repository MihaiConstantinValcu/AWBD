package com.awbd.cinema.config;

import com.awbd.cinema.services.security.JpaUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JpaUserDetailsService userDetailsService;
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests(auth -> auth
                        .requestMatchers("/product/form").hasRole("ADMIN")
                        .requestMatchers("/", "/webjars/**", "/login", "/register", "/resources/**").permitAll()
                        .requestMatchers("/product/*").authenticated()//.hasAnyRole("ADMIN", "GUEST")
                        .requestMatchers("/categories/*").hasAnyRole("ADMIN", "GUEST")
                        .anyRequest().authenticated()
                )
                .userDetailsService(userDetailsService)
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .permitAll()
                                .loginProcessingUrl("/perform_login")
                )
                .exceptionHandling(ex -> ex.accessDeniedPage("/access_denied"))
                .build();
    }
}
