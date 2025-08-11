package com.marcato.springmarcatoerp.service.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.marcato.springmarcatoerp.DTO.Department.DepartmentDTO;
import com.marcato.springmarcatoerp.config.exceptions.DomainNotFoundException;
import com.marcato.springmarcatoerp.jooq.tables.records.DepartmentRecord;
import com.marcato.springmarcatoerp.repository.DepartmentRepository;
import com.marcato.springmarcatoerp.service.entity.patch.DepartmentPatchService;
import com.marcato.springmarcatoerp.service.jsonpatch.exception.JsonPatchInternalServerError;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentPatchService patchService;
    public DepartmentService(DepartmentRepository departmentRepository, DepartmentPatchService patchService) {
        this.departmentRepository = departmentRepository;
        this.patchService = patchService;
    }

    @Transactional
    public DepartmentDTO patchDepartment(Integer id, JsonNode node) throws JsonPatchInternalServerError {
        Optional<DepartmentRecord> depRecord = departmentRepository.findById(id).join();
        if(depRecord.isEmpty()){
            throw new DomainNotFoundException();
        }
        DepartmentRecord updatedRecord = this.patchService.patchEntity(depRecord.get(),node);

        updatedRecord.store();
        return recordToDTO(updatedRecord);

    }

    private DepartmentDTO recordToDTO(DepartmentRecord record){
        return new DepartmentDTO(record.getId(), record.getTitle(), record.getDescription());
    }
}


