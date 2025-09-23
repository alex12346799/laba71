package com.example.laba71.dto;

import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private int status;
    private String reason;
    private String message;
    private String path;
    private Instant timestamp;
    private List<FieldErrorItem> errors;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class FieldErrorItem {
        private String field;
        private String error;
    }
}