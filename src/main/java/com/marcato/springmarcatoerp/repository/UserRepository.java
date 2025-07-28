package com.marcato.springmarcatoerp.repository;


import com.marcato.springmarcatoerp.jooq.tables.daos.UserErpDao;
import com.marcato.springmarcatoerp.jooq.tables.pojos.UserErpPojo;
import com.marcato.springmarcatoerp.jooq.tables.records.UserErpRecord;
import org.jooq.DSLContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.marcato.springmarcatoerp.jooq.tables.UserErp.USER_ERP;
@Repository
public class UserRepository {
    private final DSLContext create;
    public UserRepository(DSLContext dslContext){
        this.create = dslContext;
    }

    @Async("asyncVirtualThreadExecutor")
    public CompletableFuture<Optional<UserErpPojo>> getUserErpById(Integer id){
        return CompletableFuture.supplyAsync(()->new UserErpDao(this.create.configuration()).fetchOptionalById(id));
    }

    @Async("asyncVirtualThreadExecutor")
    public CompletableFuture<Optional<UserErpPojo>> getUserByUUID(UUID uuid){
        return CompletableFuture.supplyAsync(()->new UserErpDao(this.create.configuration()).fetchOptionalByUserUuid(uuid));
    }


    /*
    @Async("asyncVirtualThreadExecutor")
    public Future<List<UsererpPojo>>queryUsers(UsererpPojo criteria){

    }
    */

    @Async("asyncVirtualThreadExecutor")
    public CompletableFuture<Integer> createUser(UserErpPojo userErpPojo){
        UserErpRecord userErpRecord = create.newRecord(USER_ERP);
        userErpRecord.setUsername(userErpPojo.getUsername());
        userErpRecord.setCreatedAt(userErpPojo.getCreatedAt());
        userErpRecord.setFullName(userErpPojo.getFullName());
        userErpRecord.setUserUuid(userErpPojo.getUserUuid());
        return CompletableFuture.supplyAsync(userErpRecord::insert);
    }
}
