package com.basicTransaction_api.repository;

import com.basicTransaction_api.domain.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("""
            SELECT t FROM Transaction t
            WHERE t.sender.id = :id OR t.receiver.id = :id
            ORDER BY date DESC
            """)
    List<Transaction> findAllTransactionById(Long id);
}
