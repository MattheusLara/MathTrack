package com.solucoesludicas.mathtrack.service.impl;

import com.solucoesludicas.mathtrack.dto.UserDto;
import com.solucoesludicas.mathtrack.models.UserModel;
import com.solucoesludicas.mathtrack.repository.CriancasRepository;
import com.solucoesludicas.mathtrack.repository.UserRepository;
import com.solucoesludicas.mathtrack.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CriancasRepository criancasRepository;

    public void registerUser(UserDto data){
        var encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        var user = UserModel.builder()
                .uuid(UUID.randomUUID())
                .login(data.login())
                .email(data.email())
                .nome(data.nome())
                .password(encryptedPassword)
                .role(data.role())
                .telefone(data.telefone())
                .especialistaId(data.especialistaId())
                .build();

        userRepository.save(user);
    }

    @Override
    public void updateUser(String login, String cpf) {
        var user = userRepository.findByLogin(login);
        var crianca = criancasRepository.findByCpf(cpf);

        if(crianca == null || user == null){
          throw new RuntimeException(); //criança nao encontrada para o cpf informado
        }

        user.setCriancaUUID(crianca.getUuid());
        userRepository.save(user);
    }

}
