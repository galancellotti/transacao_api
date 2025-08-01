package com.basicTransaction_api.repository;

import com.basicTransaction_api.domain.dto.UserDTO;
import com.basicTransaction_api.domain.entity.Transaction;
import com.basicTransaction_api.domain.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private TestEntityManager manager;


    @Test
    @DisplayName("Deveria voltar sucesso quando retornar lista de transações")
    void shouldReturnAllTransactionsByUserId() {
        // Arrange
        var userDTO1 = new UserDTO(null, "Gabriel", "12345678900", "gabriel@email.com", "1234567", new BigDecimal("1000.00"));
        var userDTO2 = new UserDTO(null, "Lucas", "11122233344", "lucas@email.com", "1234567", new BigDecimal("500.00"));

        var user1 = manager.persist(new User(userDTO1));
        var user2 = manager.persist(new User(userDTO2));

        //Criando transações para validar lista e ordem
        var t1 = new Transaction(null, user1, user2, new BigDecimal(200), LocalDateTime.now().minusDays(2));
        var t2 = new Transaction(null, user2, user1, new BigDecimal(200), LocalDateTime.now().minusDays(1));
        var t3 = new Transaction(null, user1, user2, new BigDecimal(300), LocalDateTime.now());

        manager.persist(t1);
        manager.persist(t2);
        manager.persist(t3);

        // Act
        var result = repository.findAllTransactionById(user1.getId());

        // Assert
        assertThat(result).hasSize(3);
        assertThat(result.get(0).getDate()).isAfter(result.get(1).getDate());
        assertThat(result.get(1).getDate()).isAfter(result.get(2).getDate());

        assertThat(result).allMatch(t ->
                t.getSender().getId().equals(user1.getId()) || t.getReceiver().getId().equals(user1.getId())
        );
    }

    @Test
    @DisplayName("Deveria retornar lista vazia quando usuário não possui transações")
    void shouldReturnEmptyListWhenNoTransactionsFound() {
        // Arrange
        var user = manager.persist(new User(new UserDTO(null, "Usuario", "00000000000", "user@email.com", "senha", new BigDecimal("0"))));

        // Act
        var result = repository.findAllTransactionById(user.getId());

        // Assert
        assertThat(result).isEmpty();
    }
}