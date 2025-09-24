package com.example.laba71.service;

import com.example.laba71.dto.user.RegistrationDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    String register(RegistrationDto registrationDto);

    Authentication authenticate(String libraryCardNumber, String rawPassword);
}
