package com.solucoesludicas.mathtrack.dto;

import com.solucoesludicas.mathtrack.models.UserRole;

public record UserDto(String login, String password, UserRole role) {
    
}
