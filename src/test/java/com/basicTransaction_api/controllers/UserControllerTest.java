package com.basicTransaction_api.controllers;

import com.basicTransaction_api.domain.dto.UserDTO;
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
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<UserDTO> userDTO;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("Deveria voltar código 400 ao retornar uma requisição inválida")
    @WithMockUser
    void shouldRegisterUserFailure() throws Exception {
        String json = "{}";

        var response = mvc.perform(
                post("/register")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria voltar código 201 ao retornar uma requisição válida")
    @WithMockUser
    void shouldRegisterUserSuccessfully() throws Exception {
        var user = new UserDTO(1L, "gabriel", "11233355545", "gabriel@example.com", "123456", BigDecimal.valueOf(50));

        when(userService.register(any())).thenReturn(user);

        var response = mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userDTO.write(user).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

}