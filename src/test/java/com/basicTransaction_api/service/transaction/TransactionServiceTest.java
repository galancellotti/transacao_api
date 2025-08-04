package com.basicTransaction_api.service.transaction;

import com.basicTransaction_api.domain.dto.TransactionDTO;
import com.basicTransaction_api.domain.entity.User;
import com.basicTransaction_api.repository.TransactionRepository;
import com.basicTransaction_api.service.TransactionService;
import com.basicTransaction_api.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserService userService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        SecurityContextHolder.setContext(securityContext);
        lenient().when(securityContext.getAuthentication()).thenReturn(authentication);
        lenient().when(authentication.getName()).thenReturn("user@email.com");
    }

    @Test
    @DisplayName("Deveria cadastrar quando os id do sender e receiver forem diferentes")
    void shouldBeDifferent() {

        Long senderId = 1L;
        Long receiverId = 2L;
        TransactionDTO data = new TransactionDTO(senderId, receiverId, new BigDecimal(50));

        User sender = new User();
        sender.setId(senderId);
        sender.setBalance(BigDecimal.valueOf(1000));

        User receiver = new User();
        receiver.setId(receiverId);
        receiver.setBalance(BigDecimal.valueOf(500));

        when(userService.findUserById(senderId)).thenReturn(sender);
        when(userService.findUserById(receiverId)).thenReturn(receiver);

        when(userService.findByEmail("user@email.com")).thenReturn(sender);

        Assertions.assertDoesNotThrow(() -> transactionService.createTransaction(data));
    }

    @Test
    @DisplayName("Deveria lançar uma excessão quando os id do sender e receiver forem iguais")
    void shouldThrowExceptionWhenIdEqual() {
        Long userId = 1L;

        User user = new User();
        user.setId(userId);

        TransactionDTO data = new TransactionDTO(userId, userId, new BigDecimal(50));

        when(userService.findUserById(userId)).thenReturn(user);

        Assertions.assertThrows(RuntimeException.class, () -> {
            transactionService.createTransaction(data);
        });
    }
}
