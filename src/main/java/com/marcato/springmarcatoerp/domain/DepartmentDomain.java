package com.marcato.springmarcatoerp.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.marcato.springmarcatoerp.DTO.Department.DepartmentInputDTO;
import com.marcato.springmarcatoerp.jooq.tables.Department;
import com.marcato.springmarcatoerp.jooq.tables.daos.DepartmentDao;
import com.marcato.springmarcatoerp.jooq.tables.pojos.DepartmentPojo;
import com.marcato.springmarcatoerp.jooq.tables.records.DepartmentRecord;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.jooq.exception.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@Component
public class DepartmentDomain implements iEditableDomain<DepartmentPojo, DepartmentRecord> {
    private final DSLContext context;
    public static final Logger logger = LoggerFactory.getLogger(DepartmentDomain.class);
    public DepartmentDomain(DSLContext context) {
        this.context = context;
    }

    @Override
    public String getKey() {
        return "departments";
    }
    public DepartmentPojo dtoToDomain(DepartmentInputDTO dto){
        return new DepartmentPojo(null, dto.title(), dto.description());
    }

    @Override
    public Table<DepartmentRecord> getTable() {
        return Department.DEPARTMENT;
    }

    @Override
    public DepartmentPojo create(DepartmentPojo pojo) {
        DepartmentRecord departmentRecord = new DepartmentRecord(pojo);
        departmentRecord.attach(context.configuration());
        if(departmentRecord.insert() > 0){
            return departmentRecord.into(pojo);
        }
        return null;
    }

    @Override
    public DepartmentPojo read(String id) {
        return null;
    }

    @Override
    public DepartmentPojo update(String id, DepartmentPojo pojo) {
        return null;
    }

    @Override
    public DepartmentPojo patch(String id, JsonNode node) {
        return null;
    }

    @Override
    public String delete(String id) {
        Integer deletedId = context.deleteFrom((Department.DEPARTMENT)).where(Department.DEPARTMENT.ID.eq(Integer.valueOf(id))).returning(Department.DEPARTMENT.ID).execute();
        return String.valueOf(deletedId);
    }

    @Override
    public List<DepartmentPojo> getList() {
        CompletableFuture<List<DepartmentPojo>>futureDepLists = new CompletableFuture<>();
        logger.debug("Fazendo fetch");
        Thread.startVirtualThread(()-> {
            logger.debug("Fazendo fetch");
            try {
                futureDepLists.complete(context.selectFrom(Department.DEPARTMENT).fetchInto(DepartmentPojo.class));
            } catch (Exception e) {
                logger.error(e.getMessage());
                futureDepLists.complete(new ArrayList<>(0));
                throw new DataAccessException(e.getMessage());
            }
        });

        return futureDepLists.join();
    }
}
