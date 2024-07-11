package com.project.rest.usersRestApi.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.rest.usersRestApi.dto.Login;
import com.project.rest.usersRestApi.dto.Sessao;
import com.project.rest.usersRestApi.handler.BusinessException;
import com.project.rest.usersRestApi.modal.User;
import com.project.rest.usersRestApi.repository.UserRepository;
import com.project.rest.usersRestApi.security.JWTCreator;

@RestController
@RequestMapping(path = "api/public")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

   
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository repository;

    @Autowired
    private JWTCreator jwtCreator;

    @PostMapping("login")
    public Sessao logar(@RequestBody Login login) {
        logger.info("Tentativa de login para o usuário: {}", login.getUsername());

       
        User user = repository.findByUsername(login.getUsername());

        if (user != null) {
            logger.info("Usuário encontrado: {}", user.getUsername());

            boolean passwordOk = encoder.matches(login.getPassword(), user.getPassword());
            if (!passwordOk) {
                logger.error("Senha inválida para o login: {}", login.getUsername());
                throw new RuntimeException("Senha inválida para o login: " + login.getUsername());
            }

            Sessao sessao = new Sessao();
            sessao.setLogin(user.getUsername());
            sessao.setToken(jwtCreator.generateToken(user));

            logger.info("Login bem-sucedido para o usuário: {}", user.getUsername());
            return sessao;
        } else {
            logger.error("Erro ao tentar fazer login: Usuário não encontrado");
            throw new BusinessException("Erro ao tentar fazer login!");
        }
    }
}
