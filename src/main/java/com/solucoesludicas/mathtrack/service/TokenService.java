package com.solucoesludicas.mathtrack.service;

import com.solucoesludicas.mathtrack.models.UserModel;

public interface TokenService {
    public String generateToken(UserModel user);
    public String validateToken(String token);
}
