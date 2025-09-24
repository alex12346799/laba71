package com.example.laba71.repository;

import com.example.laba71.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLibraryCardNumber(String libraryCardNumber);

    boolean existsByPassportNumber(String passportNumber);
}
