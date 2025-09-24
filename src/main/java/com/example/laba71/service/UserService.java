package com.example.laba71.service;


import com.example.laba71.dto.ProfileDto;
import com.example.laba71.dto.user.RegistrationDto;
import com.example.laba71.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDate;

public interface UserService extends UserDetailsService {
    String register(com.example.laba71.dto.user.RegistrationDto registrationDto);

    Authentication authenticate(String libraryCardNumber, String rawPassword);

    User findByLibraryCardNumber(String libraryCardNumber);



    ProfileDto getProfile(String libraryCardNumber, LocalDate startDate, LocalDate endDate);
}
