package com.crm.gym.config.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig
{
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return NoOpPasswordEncoder.getInstance(); // Password not encrypted yet
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http,
                                             @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService,
                                             PasswordEncoder passwordEncoder) throws Exception
    {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);

        return authBuilder.build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain actuatorSecurityFilterChain(HttpSecurity http,
                                                           AuthenticationManager authManager) throws Exception
    {
        return http
                .securityMatcher("/actuator/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationManager(authManager)
                .authorizeHttpRequests(
                        auth -> auth.anyRequest().authenticated()
                )
                .httpBasic(withDefaults())
                .build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain appSecurityFilterChain(HttpSecurity http,
                                                      AuthenticationManager authManager) throws Exception
    {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationManager(authManager)
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers(
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html",
                                        "/swagger-resources/**",
                                        "/webjars/**"
                                ).permitAll() // Allows open access to OpenAPI docs
                                .requestMatchers(HttpMethod.POST,
                                        "/trainees",
                                        "/trainers",
                                        "/*/login"
                                ).permitAll()
                                .requestMatchers(HttpMethod.PUT,"/*/change-password")
                                .permitAll() // Allows open access to these endpoints per business rules
                                .anyRequest().authenticated() // Requires auth for all other requests
                )
                //.httpBasic(withDefaults()) // disabled to prevent conflicts with endpoints returning 401 intentionally
                .httpBasic(https ->
                        https.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .build();
    }
}
