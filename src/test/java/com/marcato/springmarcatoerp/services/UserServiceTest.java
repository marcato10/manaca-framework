package com.marcato.springmarcatoerp.services;

import com.marcato.springmarcatoerp.DTO.User.UserDTO;
import com.marcato.springmarcatoerp.repository.UserRepository;
import com.marcato.springmarcatoerp.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class UserServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("Retorna um usuario caso exista")
    void findUserById_ShouldReturnUserDTO_WhenFind(){
        Optional<UserDTO>user = userService.findUserById(1);
        assertTrue(user.isPresent());

    }
}
