package com.example.kurs.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.example.kurs.model.UserAuthority;

@Slf4j
@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(expressionInterceptUrlRegistry ->
                        expressionInterceptUrlRegistry
                                .requestMatchers("/registration", "/login").permitAll()

                                .requestMatchers( "/api/customers/**").hasAuthority(UserAuthority.customer.getAuthority())


                                .requestMatchers( "/api/employees/**").hasAuthority(UserAuthority.manager.getAuthority())


                                .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/products/**").hasAuthority(UserAuthority.seller.getAuthority())
                                .requestMatchers(HttpMethod.PUT, "/api/products/**").hasAuthority(UserAuthority.seller.getAuthority())
                                .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasAuthority(UserAuthority.seller.getAuthority())

                                .requestMatchers( "/api/cart/**").permitAll()

                                .requestMatchers(HttpMethod.GET, "/api/orders/**").hasAuthority(UserAuthority.manager.getAuthority())
                                .requestMatchers(HttpMethod.POST, "/api/orders/**").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/orders/**").hasAuthority(UserAuthority.manager.getAuthority())
                                .requestMatchers(HttpMethod.DELETE, "/api/orders/**").hasAuthority(UserAuthority.manager.getAuthority())
                                .anyRequest().hasAuthority(UserAuthority.admin.getAuthority()))
                .formLogin(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
