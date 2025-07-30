package com.basicTransaction_api.service;

import com.basicTransaction_api.domain.dto.TransactionDTO;
import com.basicTransaction_api.domain.entity.Transaction;
import com.basicTransaction_api.domain.entity.User;
import com.basicTransaction_api.domain.exceptions.ValidationExceptions;
import com.basicTransaction_api.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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

        Transaction transaction = new Transaction();
        transaction.setValue(data.value());
        transaction.setSender(sender);
        transaction.setReceiver(receiver);

        sender.setBalance(sender.getBalance().subtract(data.value()));
        receiver.setBalance(receiver.getBalance().add(data.value()));

        transactionRepository.save(transaction);

        return data;
    }
}
