package com.basicTransaction_api.controllers;

import com.basicTransaction_api.domain.dto.AuthenticationDTO;
import com.basicTransaction_api.domain.dto.UserDTO;
import com.basicTransaction_api.domain.entity.User;
import com.basicTransaction_api.security.TokenHandler;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<AuthenticationDTO> authenticationDTO;

    @Autowired
    private JacksonTester<UserDTO> userDTO;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private TokenHandler tokenHandler;


    @Test
    @DisplayName("Deveria retornar token se o user for válido")
    void shouldReturnTokenLoginSuccessfully() throws Exception {
        var email = "user@example.com";
        var password = "123456";

        User user = new User();
        user.setId(1L);
        user.setName("Gabriel");
        user.setCpf("11122233344");
        user.setEmail("gabriel@email.com");
        user.setPassword("1234");
        user.setBalance(new BigDecimal("50"));

        var authenticationResult = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        when(authenticationManager.authenticate(any())).thenReturn(authenticationResult);
        when(tokenHandler.generateToken(user)).thenReturn("fake-jwt-token");

        var response = mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authenticationDTO.write(new AuthenticationDTO(email, password)).getJson())).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("fake-jwt-token");
    }
}