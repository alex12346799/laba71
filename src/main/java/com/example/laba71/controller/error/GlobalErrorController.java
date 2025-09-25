package com.example.laba71.controller.error;

import com.example.laba71.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;

import java.time.Instant;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class GlobalErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        var webRequest = new ServletWebRequest(request);
        Map<String, Object> attrs = errorAttributes.getErrorAttributes(
                webRequest,
                ErrorAttributeOptions.of(
                        ErrorAttributeOptions.Include.MESSAGE,
                        ErrorAttributeOptions.Include.EXCEPTION,
                        ErrorAttributeOptions.Include.BINDING_ERRORS
                )
        );

        int status = (int) attrs.getOrDefault("status", 500);
        String reason = (String) attrs.getOrDefault("error", HttpStatus.valueOf(status).getReasonPhrase());
        String message = (String) attrs.getOrDefault("message", "Unexpected error");
        String path = (String) attrs.getOrDefault("path", request.getRequestURI());

        var dto = ErrorResponse.builder()
                .status(status)
                .reason(reason)
                .message(message)
                .path(path)
                .timestamp(Instant.now())
                .build();

        model.addAttribute("err", dto);
        return "error";
    }
}