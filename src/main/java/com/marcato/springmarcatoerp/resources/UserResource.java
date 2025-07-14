package com.marcato.springmarcatoerp.resources;

import com.marcato.springmarcatoerp.DTO.User.UserDTO;
import com.marcato.springmarcatoerp.DTO.User.UserDashboardResponseDTO;
import com.marcato.springmarcatoerp.DTO.User.UserRegistrationDTO;
import com.marcato.springmarcatoerp.DTO.Workspace.WorkspaceViewsDTO;
import com.marcato.springmarcatoerp.entity.tables.pojos.UsererpPojo;
import com.marcato.springmarcatoerp.service.WorkspaceService;
import jakarta.validation.Valid;
import org.jooq.exception.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.marcato.springmarcatoerp.service.UserService;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/user")
public class UserResource {
    private final UserService userService;
    private final WorkspaceService workspaceService;
    Logger logger = LoggerFactory.getLogger(UserResource.class);

    public UserResource(UserService puserService, WorkspaceService workspaceService){
        this.userService = puserService;
        this.workspaceService = workspaceService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>retrieveUserById(@AuthenticationPrincipal Jwt principal,@PathVariable String id) throws ExecutionException, InterruptedException {
        Optional<UserDTO>user = userService.findUserById(Integer.valueOf(id));
        if(user.isPresent()){
            if(!user.get().userUuid().equals(UserDTO.convertOAuthSubToUUID(principal.getId())))
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/status")
    public ResponseEntity<?> fetchUserUUID(@AuthenticationPrincipal Jwt principal) {
        UUID userSub = UserDTO.convertOAuthSubToUUID(principal.getSubject());
        Optional<UserDTO> userRecord = userService.findUserByUUID(userSub);
            if(userRecord.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            List<WorkspaceViewsDTO> workspaces = workspaceService.findWorkspaceViewsFromUser(userRecord.get().id());
            return ResponseEntity.ok(new UserDashboardResponseDTO(userRecord.get(),workspaces));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('User,Moderator,Administrator')")
    public ResponseEntity<?>createUser(@AuthenticationPrincipal Jwt principal, @Valid @RequestBody UserRegistrationDTO userDTO){
        try{
            UUID userSub = UserDTO.convertOAuthSubToUUID(principal.getSubject());
            UserDashboardResponseDTO response = userService.createUser(userSub,userDTO);
            return ResponseEntity.ok(response);
        }catch (DuplicateKeyException e){
            logger.info(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatusCode.valueOf(409));
        }
        catch (DataAccessException e){
            logger.info(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

}
