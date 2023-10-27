package com.solucoesludicas.mathtrack.service;

import com.solucoesludicas.mathtrack.dto.UserDto;

public interface UserService {
    void registerUser(UserDto data);

    void updateUser(String login, String childCpf);
}
