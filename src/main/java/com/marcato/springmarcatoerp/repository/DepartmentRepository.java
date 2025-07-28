package com.marcato.springmarcatoerp.repository;

import com.marcato.springmarcatoerp.DTO.Department.DepartmentDTO;
import com.marcato.springmarcatoerp.DTO.Department.DepartmentInputDTO;
import com.marcato.springmarcatoerp.jooq.tables.Department;
import com.marcato.springmarcatoerp.jooq.tables.daos.DepartmentDao;
import com.marcato.springmarcatoerp.jooq.tables.pojos.DepartmentPojo;
import com.marcato.springmarcatoerp.jooq.tables.records.DepartmentRecord;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.exception.DataAccessException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
@Repository
public class DepartmentRepository {
    private final DSLContext create;

    public DepartmentRepository(DSLContext create) {
        this.create = create;
    }
    //We return a DTO, because the database will generate an ID.
    @Async("asyncVirtualThreadExecutor")
    public CompletableFuture<DepartmentRecord> createDepartment(DepartmentPojo pojo){
        DepartmentRecord depRecord = create.newRecord(Department.DEPARTMENT);
        Result<DepartmentRecord> record = create.fetch(Department.DEPARTMENT);

        depRecord.setTitle(pojo.getTitle());
        depRecord.setDescription(pojo.getDescription());
        depRecord.insert();
        depRecord.touched();
        return CompletableFuture.supplyAsync(()->depRecord);
    }

    @Async("asyncVirtualThreadExecutor")
    public CompletableFuture<Optional<DepartmentPojo>>findById(Integer id){
        return CompletableFuture.supplyAsync(()->
                new DepartmentDao(this.create.configuration()).fetchOptionalById(id)
        );
    }
    //TODO


    @Async
    public CompletableFuture<List<DepartmentDTO>>getAllDepartments(){
        return CompletableFuture.supplyAsync(()->create.selectFrom(Department.DEPARTMENT).fetchInto(DepartmentDTO.class));
    }
}
