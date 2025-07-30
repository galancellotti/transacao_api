package com.basicTransaction_api.controllers;

import com.basicTransaction_api.domain.dto.UserDTO;
import com.basicTransaction_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<UserDTO> registerUser(@RequestBody @Valid UserDTO data) {
        UserDTO user = service.register(data);

        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }
}
