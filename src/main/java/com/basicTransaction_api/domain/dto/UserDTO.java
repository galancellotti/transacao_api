package com.basicTransaction_api.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UserDTO(

        Long id,

        @NotBlank(message = "nome é obrigatório")
        String name,

        @NotBlank(message = "CPF é obrigatório")
        String cpf,

        @NotBlank(message = "Email é obrigatório")
        @Email
        String email,

        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
        String password,

        @NotNull(message = "Balance é obrigatório")
        BigDecimal balance

) {
}
