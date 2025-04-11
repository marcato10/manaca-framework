package com.marcato.springmarcatoerp.resources;

import com.marcato.springmarcatoerp.entity.tables.records.UserErpRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcato.springmarcatoerp.service.UserService;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/user")
public class UserResource {
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(UserResource.class);

    public UserResource(UserService puserService){
        this.userService = puserService;
    }
    
    @GetMapping("/{uuid}")
    public ResponseEntity<UserErpRecord> getUserByUUID(@PathVariable UUID uuid){
        Future<Optional<UserErpRecord>>fetchUser = userService.getUserByKeycloakUUID(uuid);
        try{
            if(fetchUser.get().isEmpty()){
                return new ResponseEntity<>(HttpStatusCode.valueOf(404));
            }
            return new ResponseEntity<>(fetchUser.get().get(), HttpStatusCode.valueOf(200));
        }
        catch (ExecutionException | InterruptedException e) {
            logger.error("Cause:{}\nMessage:{}", e.getCause(), e.getMessage());
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }



}
