package com.marcato.springmarcatoerp.repository;

import com.marcato.springmarcatoerp.DTO.Department.DepartmentDTO;
import com.marcato.springmarcatoerp.DTO.Department.DepartmentInputDTO;
import com.marcato.springmarcatoerp.jooq.tables.Department;
import com.marcato.springmarcatoerp.jooq.tables.daos.DepartmentDao;
import com.marcato.springmarcatoerp.jooq.tables.pojos.DepartmentPojo;
import com.marcato.springmarcatoerp.jooq.tables.records.DepartmentRecord;
import org.jooq.DSLContext;
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
        depRecord.setTitle(pojo.getTitle());
        depRecord.setDescription(pojo.getDescription());
        depRecord.insert();
        depRecord.touched();
        return CompletableFuture.completedFuture(depRecord);
    }

    @Async("asyncVirtualThreadExecutor")
    public CompletableFuture<Optional<DepartmentPojo>>findById(Integer id){
        return CompletableFuture.completedFuture(
                new DepartmentDao().fetchOptionalById(id)
        );
    }

    @Async("asyncVirtualThreadExecutor")
    @Transactional
    public CompletableFuture<List<DepartmentDTO>> batchUpsert(List<DepartmentDTO> toCreate, List<DepartmentDTO> toUpdate) {


        List<DepartmentDTO> createdDtos = new ArrayList<>();
        if (toCreate != null && !toCreate.isEmpty()) {
            createdDtos = toCreate.stream()
                    .map(dto -> create.insertInto(Department.DEPARTMENT)
                            .set(Department.DEPARTMENT.TITLE, dto.title())
                            .set(Department.DEPARTMENT.DESCRIPTION, dto.description())
                            .returning()
                            .fetchOneInto(DepartmentDTO.class))
                    .toList();
        }

        if (toUpdate != null && !toUpdate.isEmpty()) {
            List<DepartmentRecord> recordsToUpdate = toUpdate.stream()
                    .map(dto -> {
                        DepartmentRecord record = new DepartmentRecord();
                        record.setId(dto.id());
                        record.setTitle(dto.title());
                        record.setDescription(dto.description());
                        return record;
                    })
                    .toList();

            create.batchUpdate(recordsToUpdate).execute();
        }

        List<DepartmentDTO> updatedDtos = new ArrayList<>();
        if (toUpdate != null && !toUpdate.isEmpty()) {
            List<Integer> updatedIds = toUpdate.stream().map(DepartmentDTO::id).toList();
            updatedDtos = create.selectFrom(Department.DEPARTMENT)
                    .where(Department.DEPARTMENT.ID.in(updatedIds))
                    .fetchInto(DepartmentDTO.class);
        }

        return CompletableFuture.completedFuture(Stream.concat(createdDtos.stream(), updatedDtos.stream()).toList());
    }

    @Async
    public CompletableFuture<List<DepartmentDTO>>getAllDepartments(){
        return CompletableFuture.completedFuture(create.selectFrom(Department.DEPARTMENT).fetchInto(DepartmentDTO.class));
    }
}
