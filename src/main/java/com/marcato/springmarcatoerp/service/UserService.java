package com.marcato.springmarcatoerp.service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.*;

import jakarta.annotation.PreDestroy;
import org.jooq.DSLContext;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.marcato.springmarcatoerp.entity.tables.UserErp;
import com.marcato.springmarcatoerp.entity.tables.records.UserErpRecord;
@Service
public class UserService {

    private final DSLContext create;

    public UserService(DSLContext dslContext){
        this.create = dslContext;
    }
    @Async
    public CompletableFuture<Optional<UserErpRecord>> getUserErpById(int id){
        return CompletableFuture.completedFuture(Optional.ofNullable
                (create.selectFrom(UserErp.USER_ERP).where(UserErp.USER_ERP.ID.eq(id)).fetchOne()));
    }
    @Async
    public CompletableFuture<Optional<UserErpRecord>> getUserByKeycloakUUID(UUID uuid){
        return CompletableFuture.completedFuture(Optional.ofNullable
                (create.selectFrom(UserErp.USER_ERP).where(UserErp.USER_ERP.KEYCLOAK_UUID.eq(uuid)).fetchOne()));
    }


}
