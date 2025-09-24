package com.example.laba71.dto.user;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, RegistrationDto> {
    @Override
    public boolean isValid(RegistrationDto dto, ConstraintValidatorContext ctx) {
        if (dto == null) return true;
        String p1 = dto.getPassword();
        String p2 = dto.getConfirmPassword();
        if (p1 == null || p2 == null) return true;
        boolean ok = p1.equals(p2);
        if (!ok) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate("Пароли не совпадают")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation();
        }
        return ok;
    }
}
