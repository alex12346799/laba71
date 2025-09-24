package com.example.laba71.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("libraryCardNumber")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/auth/login?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .logoutSuccessUrl("/auth/login?logout")
                        .permitAll()
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/error",
                                "/auth/login", "/auth/register",
                                "/api/users/search",
                                "/api/image/**",
                                "/static/**", "/assets/**",
                                "/css/**","/js/**","/img/**","/webjars/**",
                                "/h2-console/**"
                        ).permitAll()
                        .requestMatchers("/profile/**").authenticated()
                        .anyRequest().authenticated()
                )
                        .exceptionHandling(ex -> ex
                                .accessDeniedPage("/error")
                        );

        return http.build();
    }
}
