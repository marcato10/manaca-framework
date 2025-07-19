package com.marcato.springmarcatoerp.repository;

import org.jooq.DSLContext;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {
    @Mock
    private DSLContext dslContext;

    @InjectMocks
    private UserRepository userRepository;
}
