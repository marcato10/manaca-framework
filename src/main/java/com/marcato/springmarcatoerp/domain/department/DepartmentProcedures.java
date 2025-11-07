package com.marcato.springmarcatoerp.domain.department;

import com.marcato.springmarcatoerp.dataengine.core.domain.DomainProcedures;
import com.marcato.springmarcatoerp.jooq.tables.Department;
import com.marcato.springmarcatoerp.jooq.tables.pojos.DepartmentPojo;
import com.marcato.springmarcatoerp.jooq.tables.records.DepartmentRecord;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class DepartmentProcedures implements DomainProcedures<DepartmentPojo> {
    private final Table<DepartmentRecord> TABLE = Department.DEPARTMENT;
    private final DSLContext context;

    public DepartmentProcedures(DSLContext context) {
        this.context = context;
    }
    public Table<DepartmentRecord> getDepartmentTable(){
        return this.TABLE;
    }
    @Override
    public DepartmentPojo create(DepartmentPojo pojo) {
        // Delega o trabalho para a ferramenta jOOQ
        DepartmentRecord departmentRecord = context.newRecord(TABLE, pojo);
        departmentRecord.attach(context.configuration());
        departmentRecord.insert();
        return departmentRecord.into(DepartmentPojo.class);
    }

    @Override
    public DepartmentPojo read(String id) {
        return context.selectFrom(TABLE)
                .where(TABLE.field("id", Integer.class).eq(Integer.valueOf(id)))
                .fetchOneInto(DepartmentPojo.class);
    }

    @Override
    public DepartmentPojo update(String id, DepartmentPojo pojo) {
        return null;
    }

    @Override
    public String delete(String id) {
        return "";
    }

    @Override
    public List<DepartmentPojo> getList() {
        // Lógica síncrona simples. O Controller cuida do agendamento (Schedulers).
        try {
            return context.selectFrom(TABLE).fetchInto(DepartmentPojo.class);
        } catch (Exception e) {

            return new ArrayList<>(0);
        }
    }
}
