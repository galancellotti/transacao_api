package com.basicTransaction_api.domain.exceptions;

import com.basicTransaction_api.domain.dto.ResponseErrorDTO;
import com.basicTransaction_api.domain.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationExceptions.class)
    public ResponseEntity<ResponseErrorDTO> validationException(ValidationExceptions ex) {

        ResponseErrorDTO response = new ResponseErrorDTO(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(UserIdNotFoundException.class)
    public ResponseEntity<ResponseErrorDTO> userException(UserIdNotFoundException ex) {

        ResponseErrorDTO response = new ResponseErrorDTO(
                ex.getMessage(),
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseErrorDTO> treatException(Exception ex) {
        ResponseErrorDTO response = new ResponseErrorDTO(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
