package com.example.kursovaya_rabota.security.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@AllArgsConstructor
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/menu/dish/create/**", "/menu/dish/delete/**", "/menu/segment/**", "menu/dish/edit/**")
                        .hasAnyRole("ADMIN")
                        .requestMatchers("/waiter/**")
                        .hasAnyRole("ADMIN", "WAITER")
                        .requestMatchers("/cook/**")
                        .hasAnyRole("ADMIN", "COOK")
                        .requestMatchers("/analytics/**")
                        .hasAnyRole("ADMIN", "ANALYST")
                        .requestMatchers("/warehouse/**")
                        .hasAnyRole("ADMIN", "COOK")
                        .requestMatchers("/basket/**")
                        .hasAnyRole("ADMIN", "USER")
                        .anyRequest().permitAll()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()

                )
                .logout((logout) -> logout.permitAll()
                        .logoutSuccessUrl("/menu"));

        return http.build();
    }
}
