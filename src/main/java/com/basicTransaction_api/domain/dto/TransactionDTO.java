package com.basicTransaction_api.domain.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransactionDTO(

        @NotNull
        Long senderId,
        @NotNull
        Long receiverId,
        @NotNull
        BigDecimal value

) {
}
