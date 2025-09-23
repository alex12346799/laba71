package com.example.laba71.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationDto {
    private String name;

    private String surname;

    private String patronymic;

    @NotBlank(message = "Адрес обязателен")
    private String address;

    @NotBlank(message = "Номер паспорта обязателен")
    @Pattern(regexp = "[A-Z0-9]{5,20}")
    private String passportNumber;

    @NotBlank(message = "Пароль обязателен")
    @Size(min = 6, message = "Пароль должен содержать минимум 6 символов")
    private String password;

    @NotBlank(message = "Подтверждение пароля обязательно")
    private String confirmPassword;

}
