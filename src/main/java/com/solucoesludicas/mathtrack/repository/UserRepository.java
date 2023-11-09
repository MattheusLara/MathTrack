package com.solucoesludicas.mathtrack.repository;

import com.solucoesludicas.mathtrack.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findByLogin(String login);
    UserDetails findByEmail(String email);
    UserModel findByEspecialistaId(String especialistaId);

    UserModel findByCpf(String cpf);
}

