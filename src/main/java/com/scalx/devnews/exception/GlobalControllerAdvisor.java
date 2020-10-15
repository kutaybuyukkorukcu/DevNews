package com.scalx.devnews.exception;

import com.scalx.devnews.entity.Recommendation;
import com.scalx.devnews.entity.User;
import com.scalx.devnews.utils.ErrorResponse;
import com.sun.mail.iap.Response;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvisor extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(RecommendationHttpException.class)
    public ResponseEntity<Object> handleRecommendationHttpException(
            RecommendationHttpException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setDate(Date.valueOf(LocalDate.now()));
        errorResponse.setMessage("Article expired. --- The site owner changed or deleted the article");

        return ResponseEntity.ok(errorResponse);
    }

    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    public ResponseEntity<Object> handleInvalidJwtAuthenticationException(
            InvalidJwtAuthenticationException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setDate(Date.valueOf(LocalDate.now()));
        errorResponse.setMessage("Account not authorized - jwt token expired");

        return ResponseEntity.ok(errorResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setDate(Date.valueOf(LocalDate.now()));
        errorResponse.setMessage("Resource not found");

        return ResponseEntity.ok(errorResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(
            UserNotFoundException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setDate(Date.valueOf(LocalDate.now()));
        errorResponse.setMessage("User not found");

        return ResponseEntity.ok(errorResponse);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(
            UsernameNotFoundException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setDate(Date.valueOf(LocalDate.now()));
        errorResponse.setMessage("Username not found");

        return ResponseEntity.ok(errorResponse);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRunTimeException(
            RuntimeException ex, WebRequest request) {

        logger.info(ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setDate(Date.valueOf(LocalDate.now()));
        errorResponse.setMessage("Runtime exception check");

        return ResponseEntity.ok(errorResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(
            BadCredentialsException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setDate(Date.valueOf(LocalDate.now()));
        errorResponse.setMessage("Incorrect username or password");

        return ResponseEntity.ok(errorResponse);
    }
}
