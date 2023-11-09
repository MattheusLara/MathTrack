package com.solucoesludicas.mathtrack.service.impl;

import com.solucoesludicas.mathtrack.dto.UserDto;
import com.solucoesludicas.mathtrack.exception.NotFoundException;
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
                .cpf(data.cpf())
                .telefone(data.telefone())
                .especialistaId(data.especialistaId())
                .build();

        userRepository.save(user);
    }

    @Override
    public void updateUser(String userCpf, String childCpf) throws NotFoundException {
        var user = userRepository.findByCpf(userCpf);
        var crianca = criancasRepository.findByCpf(childCpf);

        if(user == null){
            throw new NotFoundException("Requisição inválida", "Usuário não o para o login informado");
        }

        if(crianca == null){
          throw new NotFoundException("Requisição inválida", "Criança não encontrada para o CPF informado");
        }

        user.setCriancaUUID(crianca.getUuid());
        userRepository.save(user);
    }

}
