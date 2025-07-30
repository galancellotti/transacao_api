package com.basicTransaction_api.domain.dto;

import com.basicTransaction_api.domain.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

    public record TransactionsDetailDTO(
            Long senderId,
            Long receiverId,
            BigDecimal value,
            LocalDateTime date
    ) {
        public TransactionsDetailDTO(Transaction transaction) {
            this(transaction.getSender().getId(), transaction.getReceiver().getId(), transaction.getValue(), transaction.getDate());
        }
    }
