package com.basicTransaction_api.domain.dto;

import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;

public record ResponseErrorDTO(String message, HttpStatus httpStatus, LocalDateTime time) {


}
