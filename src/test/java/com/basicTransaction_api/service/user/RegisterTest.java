package com.basicTransaction_api.service.user;

import com.basicTransaction_api.domain.dto.UserDTO;
import com.basicTransaction_api.domain.entity.User;
import com.basicTransaction_api.domain.exceptions.ValidationExceptions;
import com.basicTransaction_api.repository.UserRepository;
import com.basicTransaction_api.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Deveria devolver uma exception quando o balance for menor que 20")
    void shouldThrowExceptionWhenBalanceIsLessThanTwenty() {
        // Arrange
        BigDecimal initialBalance = new BigDecimal("10.00");
        UserDTO data = new UserDTO(1L, "Gabriel", "11122233345", "gabriel@email.com", "senha123", initialBalance);


        //Act & Assert
        Assertions.assertThrows(ValidationExceptions.class, () -> userService.register(data));
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deveria altorizar o cadastro quando o balance for maior ou igual a 20")
    void shouldRegisterUserWhenBalanceIsEqualOrMoreThanTwenty() {
        // Arrange
        BigDecimal initialBalance = new BigDecimal("30.00");
        UserDTO data = new UserDTO(1L, "Gabriel", "11122233345", "gabriel@email.com", "senha123", initialBalance);

        when(passwordEncoder.encode("senha123")).thenReturn("hashedSenha");

        // Act & Assert
        Assertions.assertDoesNotThrow(() -> {
            UserDTO result = userService.register(data);

            Assertions.assertEquals(data, result);

            ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
            verify(userRepository).save(captor.capture());

            User savedUser = captor.getValue();
            Assertions.assertEquals("gabriel@email.com", savedUser.getEmail());
            Assertions.assertEquals(initialBalance, savedUser.getBalance());
            Assertions.assertEquals("hashedSenha", savedUser.getPassword());
        });
    }
}