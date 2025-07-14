package com.marcato.springmarcatoerp.repository;

import com.marcato.springmarcatoerp.entity.tables.daos.UsererpDao;
import com.marcato.springmarcatoerp.entity.tables.pojos.UsererpPojo;
import com.marcato.springmarcatoerp.entity.tables.records.UsererpRecord;
import org.jooq.DSLContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.marcato.springmarcatoerp.entity.tables.Usererp.USERERP;
@Repository
public class UserRepository {
    private final DSLContext create;
    public UserRepository(DSLContext dslContext){
        this.create = dslContext;
    }

    @Async("asyncVirtualThreadExecutor")
    public CompletableFuture<Optional<UsererpPojo>> getUserErpById(Integer id){
        return CompletableFuture.completedFuture(new UsererpDao().fetchOptionalById(id));
    }

    @Async("asyncVirtualThreadExecutor")
    public CompletableFuture<Optional<UsererpPojo>> getUserByUUID(UUID uuid){
        return CompletableFuture.completedFuture(new UsererpDao().fetchOptionalByUserUuid(uuid));
    }


    /*
    @Async("asyncVirtualThreadExecutor")
    public Future<List<UsererpPojo>>queryUsers(UsererpPojo criteria){

    }
    */
    @Async("asyncVirtualThreadExecutor")
    public CompletableFuture<Integer> createUser(UsererpPojo userErpPojo){
        UsererpRecord userErpRecord = create.newRecord(USERERP);
        userErpRecord.setUsername(userErpPojo.getUsername());
        userErpRecord.setCreatedat(userErpPojo.getCreatedat());
        userErpRecord.setFullName(userErpPojo.getFullName());
        userErpRecord.setUserUuid(userErpPojo.getUserUuid());
        return CompletableFuture.completedFuture(userErpRecord.insert());
    }
}
