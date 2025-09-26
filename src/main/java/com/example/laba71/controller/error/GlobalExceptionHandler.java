package com.example.laba71.controller.error;//package com.example.laba71.controller.error;

import com.example.laba71.dto.ErrorResponse;
import com.example.laba71.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(NotFoundException ex, HttpServletRequest req, Model model) {
        model.addAttribute("err", ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .reason(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .path(req.getRequestURI())
                .timestamp(Instant.now())
                .build());
        return "error";
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleConflict(DataIntegrityViolationException ex, HttpServletRequest req, Model model) {
        log.warn("DataIntegrityViolation", ex);
        String friendly = resolveDataIntegrityMessage(ex);
        model.addAttribute("err", ErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .reason(HttpStatus.CONFLICT.getReasonPhrase())
                .message(friendly)
                .path(req.getRequestURI())
                .timestamp(Instant.now())
                .build());
        return "error";
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class, ConstraintViolationException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidation(Exception ex, HttpServletRequest req, Model model) {
        var builder = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .reason(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Ошибка валидации")
                .path(req.getRequestURI())
                .timestamp(Instant.now());

        if (ex instanceof MethodArgumentNotValidException manve) {
            List<ErrorResponse.FieldErrorItem> items = manve.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(this::toItem)
                    .collect(Collectors.toList());
            builder.errors(items);
        }
        if (ex instanceof ConstraintViolationException cve) {
            List<ErrorResponse.FieldErrorItem> items = cve.getConstraintViolations()
                    .stream()
                    .map(v -> new ErrorResponse.FieldErrorItem(
                            v.getPropertyPath() == null ? "<unknown>" : v.getPropertyPath().toString(),
                            v.getMessage()))
                    .collect(Collectors.toList());
            builder.errors(items);
        }
        model.addAttribute("err", builder.build());
        return "error";
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleForbidden(AccessDeniedException ex, HttpServletRequest req, Model model) {
        model.addAttribute("err", ErrorResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .reason(HttpStatus.FORBIDDEN.getReasonPhrase())
                .message("Доступ запрещён")
                .path(req.getRequestURI())
                .timestamp(Instant.now())
                .build());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleOther(Exception ex, HttpServletRequest req, Model model) {
        log.error("Unhandled exception", ex);
        model.addAttribute("err", ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .reason(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(safeMessage(ex))
                .path(req.getRequestURI())
                .timestamp(Instant.now())
                .build());
        return "error";
    }


    private ErrorResponse.FieldErrorItem toItem(FieldError fe) {
        return new ErrorResponse.FieldErrorItem(
                fe.getField(),
                (fe.getDefaultMessage() == null || fe.getDefaultMessage().isBlank())
                        ? "Некорректное значение"
                        : fe.getDefaultMessage()
        );
    }

    private String resolveDataIntegrityMessage(DataIntegrityViolationException ex) {
        String rootMsg = rootCause(ex).getMessage();
        String msg = (rootMsg == null) ? ex.getMessage() : rootMsg;

        String constraint = null;
        Throwable cause = ex.getCause();
        while (cause != null) {
            if (cause instanceof org.hibernate.exception.ConstraintViolationException h) {
                constraint = h.getConstraintName();
                break;
            }
            cause = cause.getCause();
        }

        if (constraint != null) {
            if (constraint.contains("FK_LOAN_ON_USER")) {
                return "Нельзя удалить пользователя: у него есть связанные займы.";
            }
            if (constraint.contains("FK_LOAN_ON_BOOK")) {
                return "Нельзя удалить книгу: по ней существуют займы.";
            }
            if (constraint.contains("ux_category_name") || constraint.contains("UX_CATEGORY_NAME")) {
                return "Категория с таким названием уже существует.";
            }
            if (constraint.contains("passport_number") || constraint.contains("UK_USERS_PASSPORT")) {
                return "Паспорт уже зарегистрирован в системе.";
            }
            if (constraint.contains("library_card_number")) {
                return "Читательский номер уже используется.";
            }
        }

        if (msg != null && msg.toLowerCase().contains("referential integrity")) {
            return "Нарушение ссылочной целостности: существуют зависимые записи.";
        }
        if (msg != null && msg.toLowerCase().contains("unique")) {
            return "Нарушение уникальности данных.";
        }

        return "Невозможно выполнить операцию из-за ограничения целостности данных.";
    }

    private Throwable rootCause(Throwable t) {
        Throwable cur = t;
        while (cur.getCause() != null && cur.getCause() != cur) {
            cur = cur.getCause();
        }
        return cur;
    }

    private String safeMessage(Exception ex) {
        String m = ex.getMessage();
        return (m == null || m.isBlank()) ? ex.getClass().getSimpleName() : m;
    }
}
