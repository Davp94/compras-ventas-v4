package com.blumbit.compras_ventas.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;

import com.blumbit.compras_ventas.common.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value={Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse<String>> handleException(Exception ex, HttpServletRequest request) {
        return new ResponseEntity<>(ErrorResponse.<String>builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(new Date().toString())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    // @ExceptionHandler(value={MethodNotAllowedException.class})
    // @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    // public ResponseEntity<ErrorResponse<String>> handleValidationException(MethodNotAllowedException ex, HttpServletRequest request) {
    //     return new ResponseEntity<>(ErrorResponse.<String>builder()
    //             .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
    //             .timestamp(new Date().toString())
    //             .message(ex.getMessage())
    //             .path(request.getRequestURI())
    //             .build(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

    // }
}
