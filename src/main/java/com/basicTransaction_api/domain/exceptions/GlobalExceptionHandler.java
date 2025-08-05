package com.basicTransaction_api.domain.exceptions;

import com.basicTransaction_api.domain.dto.ResponseErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity error400(MethodArgumentNotValidException ex) {
        var error = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(error.stream().map(ResponseDefaultErrorDTO::new).toList());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseErrorDTO> treatException(Exception ex) {
        ResponseErrorDTO response = new ResponseErrorDTO(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    public record ResponseDefaultErrorDTO(String mensagem, HttpStatus status, LocalDateTime time) {
        public ResponseDefaultErrorDTO(FieldError error) {
            this(error.getDefaultMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        }
    }
}
