package com.marcato.springmarcatoerp.utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;

public class Jwt {

    public HttpResponse<String> getAccessToken() throws UnirestException {
        return Unirest.post("https://dev-tpjedcct43j1eghk.us.auth0.com/oauth/token")
                .header("content-type", "application/json")
                .body("{\"client_id\":\"149SdamipBZOJS3EP1XCD5gebbQdZGqO\",\"client_secret\":\"BCBnp_G_-7L8Nuzx9gpXXN7SRRXtUWtuK_MOsm3tMlYO06NFdm_durRHdN1J5Tfy\",\"audience\":\"https://marcato.erp.com\",\"grant_type\":\"client_credentials\"}")
                .asString();
    }
}
