package com.basicTransaction_api.service;

import com.basicTransaction_api.domain.dto.UserDTO;
import com.basicTransaction_api.domain.entity.User;
import com.basicTransaction_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO register(UserDTO data) {

        //Validação: Balance deve ser de no mínimo de R$20 ao criar a conta
        if (data.balance().compareTo(new BigDecimal(20)) < 0) {
            throw new RuntimeException("Saldo deve ser de no mínimo R$20 para a criação da conta");
        }

        var user = new User(data);
        userRepository.save(user);
        return data;
    }

    //Validação: Balance deve ser maior que value da transação
    public void validateTransactionBalance(User sender, BigDecimal value) {
        if (sender.getBalance().compareTo(value) < 0) {
            throw new RuntimeException("Saldo Insuficiente");
        }
    }

    public User findUserById(Long id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}
