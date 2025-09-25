package com.example.laba71.controller.error;

import com.example.laba71.dto.ErrorResponse;
import com.example.laba71.exception.NotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFound(NotFoundException ex, Model model) {
        model.addAttribute("err", ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .reason(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .timestamp(Instant.now())
                .build());
        return "error";
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public String handleValidation(Exception ex, Model model) {
        var builder = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .reason(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Ошибка валидации")
                .timestamp(Instant.now());

        if (ex instanceof MethodArgumentNotValidException manve) {
            var items = manve.getBindingResult().getFieldErrors().stream()
                    .map(fe -> new ErrorResponse.FieldErrorItem(fe.getField(), fe.getDefaultMessage()))
                    .collect(Collectors.toList());
            builder.errors(items);
        }
        model.addAttribute("err", builder.build());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleOther(Exception ex, Model model) {
        model.addAttribute("err", ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .reason(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(ex.getMessage())
                .timestamp(Instant.now())
                .build());
        return "error";
    }
}