package com.marcato.springmarcatoerp.DTO.Department;

import com.marcato.springmarcatoerp.jooq.tables.pojos.DepartmentPojo;

public record DepartmentDTO(Integer id, String title, String description){
    public static DepartmentPojo toPojo(DepartmentDTO dto){
        return new DepartmentPojo(dto.id(),dto.title(), dto.description());
    }
}
