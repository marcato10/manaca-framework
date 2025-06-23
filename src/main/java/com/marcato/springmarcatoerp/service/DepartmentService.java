package com.marcato.springmarcatoerp.service;

import com.marcato.springmarcatoerp.DTO.Department.DepartmentDTO;
import com.marcato.springmarcatoerp.DTO.Department.DepartmentInputDTO;
import com.marcato.springmarcatoerp.entity.tables.Department;
import com.marcato.springmarcatoerp.entity.tables.pojos.DepartmentPojo;
import com.marcato.springmarcatoerp.entity.tables.records.DepartmentRecord;
import org.jooq.DSLContext;
import org.jooq.InsertQuery;
import org.jooq.UpdateQuery;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@Service
public class DepartmentService {
    private final DSLContext create;

    public DepartmentService(DSLContext create) {
        this.create = create;
    }

    @Async
    public CompletableFuture<Integer>createDepartment(DepartmentPojo pojo){
        DepartmentRecord depRecord = create.newRecord(Department.DEPARTMENT);
        depRecord.setTitle(pojo.getTitle());
        depRecord.setDescription(pojo.getDescription());

        return CompletableFuture.completedFuture(depRecord.store());
    }

    @Async
    @Transactional
    public CompletableFuture<List<DepartmentDTO>> batchUpsert(List<DepartmentInputDTO> toCreate, List<DepartmentInputDTO> toUpdate) {


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
                List<Integer> updatedIds = toUpdate.stream().map(DepartmentInputDTO::id).toList();
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
