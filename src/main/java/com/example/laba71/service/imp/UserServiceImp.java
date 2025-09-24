package com.example.laba71.service.imp;

import com.example.laba71.dto.LoanViewDto;
import com.example.laba71.dto.ProfileDto;
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

import java.time.LocalDate;
import java.util.List;
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
    public String register(com.example.laba71.dto.user.RegistrationDto registrationDto) {
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
        user.setRoleName(RoleName.ROLE_READER.name());
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
                    userOpt.get().getPassword()
            );
        }
        return null;
    }

    @Override
    public User findByLibraryCardNumber(String libraryCardNumber) {
        return userRepository.findByLibraryCardNumber(libraryCardNumber).orElse(null);
    }
@Override
public ProfileDto getProfile(String libraryCardNumber, LocalDate startDate, LocalDate endDate) {
        User user = findByLibraryCardNumber(libraryCardNumber);
        if (user == null) return null;

        ProfileDto profileDto = ProfileDto.builder()
                .readerName(user.getName())
                .libraryCardNumber(user.getLibraryCardNumber())
                .fromDate(startDate)
                .toDate(endDate)
                .build();

        // Преобразуем займы
        List<LoanViewDto> loans = user.getLoans() != null
                ? user.getLoans().stream()
                .map(loan -> LoanViewDto.builder()
                        .loanId(loan.getId())
                        .bookId(loan.getBook() != null ? loan.getBook().getId() : null)
                        .title(loan.getBook() != null ? loan.getBook().getTitle() : null)
                        .author(loan.getBook() != null ? loan.getBook().getAuthor() : null)
                        .borrowDate(loan.getBorrowDate())
                        .dueDate(loan.getDueDate())
                        .returnedAt(loan.getReturnedAt())
                        .status(loan.getStatus() != null ? loan.getStatus().name() : null)
                        .build()
                )
                // Фильтруем по дате, если указано
                .filter(loan -> (startDate == null || !loan.getBorrowDate().isBefore(startDate)) &&
                        (endDate == null || !loan.getBorrowDate().isAfter(endDate)))
                .toList()
                : List.of();

        profileDto.setLoans(loans);
        return profileDto;
    }

}






