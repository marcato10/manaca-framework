package com.marcato.springmarcatoerp.resources;

import com.marcato.springmarcatoerp.DTO.User.UserDTO;
import com.marcato.springmarcatoerp.DTO.User.UserRegistrationDTO;
import com.marcato.springmarcatoerp.entity.tables.pojos.UserErpPojo;
import com.marcato.springmarcatoerp.entity.tables.records.UserErpRecord;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.marcato.springmarcatoerp.service.UserService;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/user")
public class UserResource {
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(UserResource.class);

    public UserResource(UserService puserService){
        this.userService = puserService;
    }
    
    @GetMapping("/status")
    public ResponseEntity<?> fetchUserUUID(@AuthenticationPrincipal Jwt principal) throws ExecutionException, InterruptedException {
        UUID userSub = UserDTO.convertOAuthSubToUUID(principal.getSubject());
        try{
            Optional<UserErpRecord> userRecord = userService.getUserByUUID(userSub).get();
            if(userRecord.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            UserDTO userDTO = UserDTO.fromRecord(userRecord.get());
            return ResponseEntity.ok(userDTO);

        }catch (ExecutionException | InterruptedException e){
            logger.error("Thread execution error");
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(500));
        }
        catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(500));

        }
    }

    @PostMapping("/register")
    @PreAuthorize("hasAnyAuthority('User,Moderator,Administrator')")
    public ResponseEntity<String>createUser(@AuthenticationPrincipal Jwt principal, @Valid @RequestBody UserRegistrationDTO userDTO){

        try{
            UUID userSub = UserDTO.convertOAuthSubToUUID(principal.getSubject());
            if(userService.getUserByUUID(userSub).get().isPresent()){
                return new ResponseEntity<>("User already registered",HttpStatusCode.valueOf(409));
            }
            UserErpPojo userPojo = new UserErpPojo();
            userPojo.setUserUuid(userSub);
            userPojo.setUsername(userDTO.userName());
            userPojo.setFullName(userDTO.fullName());
            userPojo.setCreatedAt(OffsetDateTime.now());
            logger.info("Trying to Insert User");
            if(userService.createUser(userPojo).get() > 0){
                logger.info("User Inserted With Success");
                return new ResponseEntity<>("User Registration was a success.",HttpStatusCode.valueOf(201));
            }
            logger.warn("User was not registered");

            return new ResponseEntity<>("User Registration not happened.",HttpStatusCode.valueOf(500));
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>("Error: " + e.getMessage(),HttpStatusCode.valueOf(500));
        }
    }

}
