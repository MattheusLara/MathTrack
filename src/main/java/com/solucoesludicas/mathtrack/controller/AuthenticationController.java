package com.solucoesludicas.mathtrack.controller;

import com.solucoesludicas.mathtrack.dto.AuthenticationDto;
import com.solucoesludicas.mathtrack.dto.LoginResponseDto;
import com.solucoesludicas.mathtrack.dto.UserDto;
import com.solucoesludicas.mathtrack.models.UserModel;
import com.solucoesludicas.mathtrack.repository.UserRepository;
import com.solucoesludicas.mathtrack.service.TokenService;
import com.solucoesludicas.mathtrack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("user")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDto data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((UserModel) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid UserDto data){
        if(!data.password().equals(data.repeatPassword())){
            return ResponseEntity.badRequest().build();
        }

        if((this.userRepository.findByLogin(data.login()) != null) || this.userRepository.findByEmail(data.email()) != null){
            return ResponseEntity.badRequest().build();
        }

        try {
            this.userService.registerUser(data);
        }  catch (Exception e) {
            throw new RuntimeException();
        }

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/link/{login}/child")
    public ResponseEntity update(@PathVariable String login, @RequestBody String cpf){
        this.userService.updateUser(login, cpf);
        return ResponseEntity.ok().build();
    }
}
