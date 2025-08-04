package com.basicTransaction_api.service;

import com.basicTransaction_api.domain.dto.TransactionDTO;
import com.basicTransaction_api.domain.dto.TransactionsDetailDTO;
import com.basicTransaction_api.domain.entity.Transaction;
import com.basicTransaction_api.domain.entity.User;
import com.basicTransaction_api.domain.exceptions.ValidationExceptions;
import com.basicTransaction_api.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    public TransactionDTO createTransaction(TransactionDTO data) {
        User sender = userService.findUserById(data.senderId());
        User receiver = userService.findUserById(data.receiverId());

        //Validação: User service
        userService.validateTransactionBalance(sender, data.value());

        //Validação: Transferencia não pode ser para o mesmo id
        if (sender.getId().equals(data.receiverId())) {
            throw new ValidationExceptions("Transferencia não pode ser para o mesmo id");
        }

        //Validação tranferencia não pode ser menor ou igual a zero
        if (data.value().compareTo(new BigDecimal(0)) <= 0) {
            throw new ValidationExceptions("Transferencia não pode ser negativa ou igual a 0");
        }

        //Validação: Verifica se o subject do token Jwt é o mesmo do sender da transação
        String emailAuthenticated = SecurityContextHolder.getContext().getAuthentication().getName();
        User userAuthenticated = userService.findByEmail(emailAuthenticated.toLowerCase());

        if (!userAuthenticated.getId().equals(data.senderId())) {
            throw new ValidationExceptions("Token Jwt não corresponse ao sender da transação");
        }

        //Cria a transação
        Transaction transaction = new Transaction();
        transaction.setValue(data.value());
        transaction.setSender(sender);
        transaction.setReceiver(receiver);

        sender.setBalance(sender.getBalance().subtract(data.value()));
        receiver.setBalance(receiver.getBalance().add(data.value()));

        transactionRepository.save(transaction);

        return data;
    }

    public List<TransactionsDetailDTO> getAllTransactions(Long id) {
        //Verifica se existe esse id no BD
        userService.findUserById(id);

        //Retorna a lista de transações do Id
        return transactionRepository.findAllTransactionById(id).stream()
                .map(TransactionsDetailDTO::new)
                .toList();
    }
}
