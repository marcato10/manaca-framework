package com.marcato.springmarcatoerp.resources;

import com.marcato.springmarcatoerp.security.SecurityConfig;
import com.marcato.springmarcatoerp.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserResource.class)
@Import(SecurityConfig.class)
public class UserResourceTest {
    @Autowired
    private MockMvc mockMvc;


}
