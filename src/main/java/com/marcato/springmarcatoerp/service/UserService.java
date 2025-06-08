package com.marcato.springmarcatoerp.service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.*;

import com.marcato.springmarcatoerp.entity.tables.UserErp;
import com.marcato.springmarcatoerp.entity.tables.pojos.UserErpPojo;
import com.marcato.springmarcatoerp.entity.tables.records.UserErpRecord;
import org.jooq.DSLContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final DSLContext create;

    public UserService(DSLContext dslContext){
        this.create = dslContext;
    }
    @Async
    public CompletableFuture<Optional<UserErpRecord>> getUserErpById(int id){
        return CompletableFuture.completedFuture(Optional.ofNullable
                (create.selectFrom(UserErp.USERERP).where(UserErp.USERERP.ID.eq(id)).fetchOne()));
    }
    @Async
    public CompletableFuture<Optional<UserErpRecord>> getUserByUUID(UUID uuid){
        return CompletableFuture.completedFuture(Optional.ofNullable
                (create.selectFrom(UserErp.USERERP).where(UserErp.USERERP.USER_UUID.eq(uuid)).fetchOne()));
    }

    @Async
    public CompletableFuture<Integer> createUser(UserErpPojo userErpPojo){
            UserErpRecord userErpRecord = create.newRecord(UserErp.USERERP);
            userErpRecord.setUsername(userErpPojo.getUsername());
            userErpRecord.setCreatedAt(userErpPojo.getCreatedAt());
            userErpRecord.setFullName(userErpPojo.getFullName());
            userErpRecord.setUserUuid(userErpPojo.getUserUuid());
        return CompletableFuture.completedFuture(userErpRecord.store());
    }

}
