package com.mybakery.exception;

import com.mybakery.service.ProductNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Exception handler for Thymeleaf web controllers.
 * Shows a friendly error page instead of the default whitelabel error screen.
 */
@ControllerAdvice(assignableTypes = com.mybakery.controller.AdminProductController.class)
public class WebExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public String handleNotFound(ProductNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/not-found";
    }
}
