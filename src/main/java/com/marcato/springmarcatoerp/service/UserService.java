package com.marcato.springmarcatoerp.service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.*;

import com.marcato.springmarcatoerp.DTO.User.UserDTO;
import com.marcato.springmarcatoerp.entity.tables.pojos.UsererpPojo;
import com.marcato.springmarcatoerp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserDTO>findUserById(Integer id) {

        Optional<UsererpPojo>userResponse = userRepository.getUserErpById(id).join();
        if(userResponse.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(UserDTO.fromPojo(userResponse.get()));

    }

    public Optional<UserDTO>findUserByUUID(UUID uuid) {
        try{
            Future<Optional<UsererpPojo>>userResponse = userRepository.getUserByUUID(uuid);
            if(userResponse.get().isEmpty()){
                return Optional.empty();
            }
            return Optional.of(UserDTO.fromPojo(userResponse.get().get()));
        } catch (Exception e){
            logger.error(e.getMessage());
            return Optional.empty();
        }
    }
}
