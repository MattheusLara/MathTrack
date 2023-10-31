package com.solucoesludicas.mathtrack.service;

import com.solucoesludicas.mathtrack.dto.UserDto;
import com.solucoesludicas.mathtrack.exception.NotFoundException;

public interface UserService {
    void registerUser(UserDto data);

    void updateUser(String login, String childCpf) throws NotFoundException;
}
