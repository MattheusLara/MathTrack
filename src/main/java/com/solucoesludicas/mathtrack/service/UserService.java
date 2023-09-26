package com.solucoesludicas.mathtrack.service;

import com.solucoesludicas.mathtrack.dto.UserDto;
import com.solucoesludicas.mathtrack.models.UserModel;

public interface UserService {
    public UserModel registerUser(UserDto data);
}
