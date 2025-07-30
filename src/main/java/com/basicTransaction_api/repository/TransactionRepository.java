package com.basicTransaction_api.repository;

import com.basicTransaction_api.demain.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
