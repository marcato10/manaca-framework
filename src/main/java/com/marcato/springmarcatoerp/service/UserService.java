package com.marcato.springmarcatoerp.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import com.marcato.springmarcatoerp.DTO.User.UserDTO;
import com.marcato.springmarcatoerp.DTO.User.UserDashboardResponseDTO;
import com.marcato.springmarcatoerp.DTO.User.UserRegistrationDTO;
import com.marcato.springmarcatoerp.DTO.Workspace.WorkspaceViewsDTO;
import com.marcato.springmarcatoerp.jooq.tables.pojos.UsererpPojo;
import com.marcato.springmarcatoerp.repository.UserRepository;
import org.jooq.exception.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(UserService.class);
    private boolean isInsertSucess(int code){
        return code == 1;
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserDTO>findUserById(Integer id) {
        Optional<UsererpPojo>userResponse = userRepository.getUserErpById(id).join();
        return userResponse.map(UserDTO::fromPojo);

    }

    public Optional<UserDTO>findUserByUUID(UUID uuid) {
        Optional<UsererpPojo>userResponse = userRepository.getUserByUUID(uuid).join();
        return userResponse.map(UserDTO::fromPojo);
    }

    @Transactional
    public UserDashboardResponseDTO createUser(UUID uuid,UserRegistrationDTO userDTO) throws DuplicateKeyException {
        if(this.findUserByUUID(uuid).isPresent()){
            throw new DuplicateKeyException("User with UUID: "+uuid+ " already exists.");
        }
        UsererpPojo userPojo = new UsererpPojo();
        userPojo.setUserUuid(uuid);
        userPojo.setUsername(userDTO.userName());
        userPojo.setFullName(userDTO.fullName());
        userPojo.setCreatedat(OffsetDateTime.now());
        logger.info("Trying to Insert User");
        int code = this.userRepository.createUser(userPojo).join();
        if(!isInsertSucess(code)){
            logger.error("Query execution failed.");
            throw new DataAccessException("Query execution failed.");
        }
        return new UserDashboardResponseDTO(UserDTO.fromPojo(userPojo),new ArrayList<WorkspaceViewsDTO>(0));
    }


}
