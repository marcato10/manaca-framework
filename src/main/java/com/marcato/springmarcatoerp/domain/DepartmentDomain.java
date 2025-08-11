package com.marcato.springmarcatoerp.domain;

import com.marcato.springmarcatoerp.jooq.tables.Department;
import com.marcato.springmarcatoerp.jooq.tables.pojos.DepartmentPojo;
import com.marcato.springmarcatoerp.jooq.tables.records.DepartmentRecord;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
public class DepartmentDomain implements iEditableDomain<DepartmentPojo, DepartmentRecord> {

    @Override
    public String getKey() {
        return "departments";
    }

    @Override
    public Table<DepartmentRecord> getTable() {
        return Department.DEPARTMENT.asTable();
    }

    @Override
    public void validate(DepartmentPojo pojo) {

    }

    @Override
    public DepartmentPojo save(DepartmentPojo pojo, DSLContext context) {

        return null;
    }

    @Override
    public DepartmentPojo create(DepartmentPojo pojo) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void handleRealtimeUpdates(WebSocketSession session, TextMessage message, String topic, String key) {

    }
}
