package com.example.laba71.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .httpBasic(Customizer.withDefaults())
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
                        .permitAll()
                )
                .csrf(csrf -> csrf.
                        ignoringRequestMatchers("/api/**"))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers(
                                "/", "/error",
                                "/auth/login", "/auth/register",
                                "/api/users/search",
                                "/api/image/**",
                                "/static/**", "/assets/**",
                                "/css/**","/js/**","/img/**","/webjars/**",
                                "/h2-console/**",
                                "/api/books/search",
                                "/logout",
                                "auth/logout",
                                "favicon.ico",
                                "favicon"
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
