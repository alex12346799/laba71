package com.example.laba71.service.impl;

import com.example.laba71.dto.user.RegistrationDto;
import com.example.laba71.model.RoleName;
import com.example.laba71.model.User;
import com.example.laba71.repository.UserRepository;
import com.example.laba71.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByLibraryCardNumber(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getLibraryCardNumber(),
                user.getPassword(),
                user.getAuthorities()
        );
    }

    @Override
    public String register(RegistrationDto registrationDto) {
        log.info("Регистрация нового пользователя с паспортом: {}", registrationDto.getPassportNumber());
        if (userRepository.existsByPassportNumber(registrationDto.getPassportNumber())) {
            log.warn("Ошибка: паспорт {} уже существует в системе", registrationDto.getPassportNumber());
            return "Пользователь с таким номером паспорта уже существует!";
        }
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            log.warn("Ошибка: пароли не совпадают для паспорта {}", registrationDto.getPassportNumber());
            return "Пароли не совпадают!";
        }
        User user = new User();
        user.setName(registrationDto.getName());
        user.setSurname(registrationDto.getSurname());
        user.setPatronymic(registrationDto.getPatronymic());
        user.setAddress(registrationDto.getAddress());
        user.setPassportNumber(registrationDto.getPassportNumber());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setLibraryCardNumber(UUID.randomUUID().toString().substring(0, 10));
        user.setRoleName(RoleName.ROLE_READER);
        user.setEnabled(true);
        userRepository.save(user);
        log.info("Пользователь {} успешно зарегистрирован", registrationDto.getPassportNumber());
        return "Пользователь успешно зарегистрирован!";

    }

    @Override
    public Authentication authenticate(String libraryCardNumber, String rawPassword) {
        Optional<User> userOpt = userRepository.findByLibraryCardNumber(libraryCardNumber);
        if (userOpt.isPresent() && passwordEncoder.matches(rawPassword, userOpt.get().getPassword())) {
            User user = userOpt.get();
            return new UsernamePasswordAuthenticationToken(
                    user.getLibraryCardNumber(),
                    null,
                    user.getAuthorities()
            );
        }
        return null;
    }


}






