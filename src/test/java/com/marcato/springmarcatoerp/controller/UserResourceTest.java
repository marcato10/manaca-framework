package com.marcato.springmarcatoerp.controller;

import com.marcato.springmarcatoerp.security.SecurityConfig;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
@SpringBootTest
public class UserResourceTest {
    private static String accessToken;

    @BeforeAll
    public static void setUp() throws UnirestException {
        HttpResponse<String> response = Unirest.post("https://dev-tpjedcct43j1eghk.us.auth0.com/oauth/token")
                .header("content-type", "application/json")
                .body("{\"client_id\":\"149SdamipBZOJS3EP1XCD5gebbQdZGqO\",\"client_secret\":\"BCBnp_G_-7L8Nuzx9gpXXN7SRRXtUWtuK_MOsm3tMlYO06NFdm_durRHdN1J5Tfy\",\"audience\":\"https://marcato.erp.com\",\"grant_type\":\"client_credentials\"}")
                .asString();
        accessToken = response.getBody();
        System.out.println(accessToken);
    }

    @Test
    void testAccessToken_notNull(){
        assertThat(accessToken).isNotEmpty();
    }
}
