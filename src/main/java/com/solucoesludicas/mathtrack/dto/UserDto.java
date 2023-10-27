package com.solucoesludicas.mathtrack.dto;

import com.solucoesludicas.mathtrack.models.UserRole;

public record UserDto(String login, String nome, String email, String telefone, String especialistaId, String password, String repeatPassword, UserRole role) {
    
}
