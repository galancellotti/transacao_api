package com.basicTransaction_api.service;

import com.basicTransaction_api.demain.dto.UserDTO;
import com.basicTransaction_api.demain.entity.User;
import com.basicTransaction_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO registerUser(UserDTO data) {

        //Validação: Balance deve ser de no mínimo de R$20 ao criar a conta
        if (data.balance().compareTo(new BigDecimal(20)) < 0) {
            throw new RuntimeException("Saldo deve ser de no mínimo R$20 para a criação da conta");
        }

        var user = new User(data);
        userRepository.save(user);
        return data;
    }


}
