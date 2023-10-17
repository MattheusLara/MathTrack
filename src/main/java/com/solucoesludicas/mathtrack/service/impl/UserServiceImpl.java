package com.solucoesludicas.mathtrack.service.impl;

import com.solucoesludicas.mathtrack.dto.UserDto;
import com.solucoesludicas.mathtrack.models.UserModel;
import com.solucoesludicas.mathtrack.repository.UserRepository;
import com.solucoesludicas.mathtrack.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserModel registerUser(UserDto data){
        var encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        var user = new UserModel(data.login(), data.email(), encryptedPassword, data.role());
        this.userRepository.save(user);
        return user;
    }

}
