package com.basicTransaction_api.service.user;

import com.basicTransaction_api.domain.entity.User;
import com.basicTransaction_api.domain.exceptions.ValidationExceptions;
import com.basicTransaction_api.repository.UserRepository;
import com.basicTransaction_api.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class ValidationTransactionTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;


    @Test
    @DisplayName("Deveria lançar uma exception se o balance for menor que o value da transação")
    void shouldThrowExceptionWhenBalanceIsInsufficient() {
        // Arrange
        User sender = new User();
        sender.setBalance(new BigDecimal("100.00"));

        // Act & Assert
        Assertions.assertThrows(ValidationExceptions.class, () -> userService.validateTransactionBalance(sender, new BigDecimal("200.00")));
    }


    @Test
    @DisplayName("Deveria altorizar a transação se o balance for maior que o value da transferencia")
    void validateTransactionBalance() {
        // Arrange
        User sender = new User();
        sender.setBalance(new BigDecimal("1000.00"));

        // Act & Assert
        Assertions.assertDoesNotThrow(() -> userService.validateTransactionBalance(sender, new BigDecimal("50.00")));
    }


}