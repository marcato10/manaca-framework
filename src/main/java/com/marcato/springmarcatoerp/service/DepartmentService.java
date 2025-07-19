package com.marcato.springmarcatoerp.service;

import com.marcato.springmarcatoerp.DTO.Department.DepartmentDTO;
import com.marcato.springmarcatoerp.DTO.Department.DepartmentInputDTO;
import com.marcato.springmarcatoerp.jooq.tables.pojos.DepartmentPojo;
import com.marcato.springmarcatoerp.jooq.tables.records.DepartmentRecord;
import com.marcato.springmarcatoerp.repository.DepartmentRepository;
import org.jooq.exception.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService implements iService<DepartmentPojo, DepartmentInputDTO, DepartmentDTO,Integer> {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public DepartmentDTO create(Optional<Integer> id, DepartmentInputDTO inputDTO) throws DuplicateKeyException {
        DepartmentPojo pojo = new DepartmentPojo(null, inputDTO.description(), inputDTO.description());
        DepartmentRecord record = this.departmentRepository.createDepartment(pojo).join();
        if(record.getId() == null){
            throw new DataAccessException("Query execution failed.");
        }
        return new DepartmentDTO(record.getId(), record.getTitle(), record.getDescription());
    }

    @Override
    public Optional<DepartmentDTO> findByID(Integer identifier) {
        Optional<DepartmentPojo>request = departmentRepository.findById(identifier).join();
        return request.map(departmentPojo -> new DepartmentDTO(departmentPojo.getId(), departmentPojo.getTitle(), departmentPojo.getDescription()));
    }
    //TODO
    @Override
    public List<DepartmentPojo> findAll() {
        return List.of();
    }

    @Override
    public DepartmentDTO batchUpsert() {
        return null;
    }

}


