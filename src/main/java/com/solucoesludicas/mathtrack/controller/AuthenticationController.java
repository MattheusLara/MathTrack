package com.solucoesludicas.mathtrack.controller;

import com.solucoesludicas.mathtrack.dto.AuthenticationDto;
import com.solucoesludicas.mathtrack.dto.LoginResponseDto;
import com.solucoesludicas.mathtrack.dto.UpdateUserDto;
import com.solucoesludicas.mathtrack.dto.UserDto;
import com.solucoesludicas.mathtrack.exception.BadRequestException;
import com.solucoesludicas.mathtrack.exception.NotFoundException;
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
        return ResponseEntity.ok(new LoginResponseDto(token, data.login()));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid UserDto data) throws BadRequestException {
        if(!data.password().equals(data.repeatPassword())){
            throw new BadRequestException("Requisição inválida", "As senhas informadas não conferem");
        }

        if((this.userRepository.findByLogin(data.login()) != null) || this.userRepository.findByEmail(data.email()) != null){
            throw new BadRequestException("Requisição inválida", "Login ou email já cadastrado");
        }

        try {
            this.userService.registerUser(data);
        }  catch (Exception e) {
            throw new BadRequestException("Requisição inválida", "Erro no registro de o usuário, por favor contate o administrador");
        }

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/link/{login}/child")
    public ResponseEntity update(@PathVariable String login, @RequestBody UpdateUserDto updateUserDto) throws NotFoundException {
        this.userService.updateUser(login, updateUserDto.getCpf());
        return ResponseEntity.ok().build();
    }
}
