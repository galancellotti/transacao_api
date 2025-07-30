package com.basicTransaction_api.controllers;

import com.basicTransaction_api.domain.dto.TransactionDTO;
import com.basicTransaction_api.domain.dto.TransactionsDetailDTO;
import com.basicTransaction_api.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping
    public ResponseEntity<TransactionDTO> registerTransaction(@RequestBody @Valid TransactionDTO data) {
        TransactionDTO newTransaction = service.createTransaction(data);

        return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<TransactionsDetailDTO>> getTransactions(@PathVariable Long id) {
        var transactions = service.getAllTransactions(id);

        return ResponseEntity.ok(transactions);
    }
}
