package com.basicTransaction_api.controllers;

import com.basicTransaction_api.domain.dto.TransactionDTO;
import com.basicTransaction_api.service.TransactionService;
import com.basicTransaction_api.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class TransactionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private UserService userService;

    @Autowired
    private JacksonTester<TransactionDTO> transactionDTO;

    @Test
    @DisplayName("Deve retornar 400 ao registrar transação inválida")
    @WithMockUser
    void shouldRegisterTransactionFailure() throws Exception {
        String json = "{}";

        var response = mvc.perform(post("/transaction")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deve retornar 201 ao registrar transação válida")
    @WithMockUser
    void shouldRegisterTransactionSuccessfully() throws Exception {
        var transaction = new TransactionDTO(1L, 1L, new BigDecimal(30));

        when(transactionService.createTransaction(any())).thenReturn(transaction);

        var response = mvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(transactionDTO.write(transaction).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }
}