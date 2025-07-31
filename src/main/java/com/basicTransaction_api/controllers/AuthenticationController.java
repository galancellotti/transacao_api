package com.basicTransaction_api.controllers;

import com.basicTransaction_api.domain.dto.AuthenticationDTO;
import com.basicTransaction_api.domain.dto.TokenDTO;
import com.basicTransaction_api.domain.dto.UserDTO;
import com.basicTransaction_api.domain.entity.User;
import com.basicTransaction_api.security.TokenHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenHandler tokenHandler;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var authenticationUser = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var token = manager.authenticate(authenticationUser);

        var tokenJwt = tokenHandler.generateToken((User) token.getPrincipal());

        return ResponseEntity.ok(new TokenDTO(tokenJwt));
    }
}
