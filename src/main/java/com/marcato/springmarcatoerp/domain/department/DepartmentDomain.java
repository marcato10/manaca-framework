package com.marcato.springmarcatoerp.domain.department;

import com.marcato.springmarcatoerp.DTO.Department.DepartmentInputDTO;
import com.marcato.springmarcatoerp.dataengine.core.BusinessDomain;
import com.marcato.springmarcatoerp.dataengine.core.DTO.FieldDefinition;
import com.marcato.springmarcatoerp.dataengine.core.domain.DomainProcedures;
import com.marcato.springmarcatoerp.jooq.tables.Department;
import com.marcato.springmarcatoerp.jooq.tables.pojos.DepartmentPojo;
import com.marcato.springmarcatoerp.jooq.tables.records.DepartmentRecord;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DepartmentDomain implements BusinessDomain<DepartmentPojo> {
    public static final Logger logger = LoggerFactory.getLogger(DepartmentDomain.class);
    private final DepartmentProcedures departmentProcedures;

    public DepartmentDomain(DepartmentProcedures departmentProcedures) {
        this.departmentProcedures = departmentProcedures;
    }


    @Override
    public String getKey() {
        return "departments";
    }

    @Override
    public DomainProcedures<DepartmentPojo> getDomainProcedures() {
        return null;
    }

    @Override
    public List<FieldDefinition> getDomainFields() {
        // A implementação concreta usa jOOQ (TABLE) para responder à pergunta pura
        return Arrays.stream(departmentProcedures.getDepartmentTable().fields())
                .map(field -> new FieldDefinition(field.getName(), field.getDataType().getTypeName()))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, String> getProceduresByPermission() {
        return Map.of(
                "read:" + getKey(), ".read",
                "create:" + getKey(), ".create",
                "update:" + getKey(), ".update",
                "delete:" + getKey(), ".delete",
                "list:" + getKey(), ".list"
                // Ex: "report:" + getKey(), ".generateReport"
        );
    }

    public DepartmentPojo dtoToDomain(DepartmentInputDTO dto){
        return new DepartmentPojo(null, dto.title(), dto.description());
    }

}
