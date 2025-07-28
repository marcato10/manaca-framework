package com.marcato.springmarcatoerp.service.entity;

import com.marcato.springmarcatoerp.DTO.Department.DepartmentDTO;
import com.marcato.springmarcatoerp.DTO.Department.DepartmentInputDTO;
import com.marcato.springmarcatoerp.bean.DepartmentBean;
import com.marcato.springmarcatoerp.jooq.tables.Department;
import com.marcato.springmarcatoerp.jooq.tables.pojos.DepartmentPojo;
import com.marcato.springmarcatoerp.jooq.tables.records.DepartmentRecord;
import com.marcato.springmarcatoerp.repository.DepartmentRepository;
import com.marcato.springmarcatoerp.service.iService;
import com.marcato.springmarcatoerp.service.jsonpatch.JsonPatchHandler;
import org.jooq.Cursor;
import org.jooq.exception.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService extends AbstractEntityPatchService<Department,DepartmentPojo> {
    private final DepartmentRepository departmentRepository;
    public DepartmentService(DepartmentRepository departmentRepository, JsonPatchHandler patchHandler, Cursor<DepartmentRecord> record) {
        super(patchHandler);
        this.departmentRepository = departmentRepository;
    }

    public DepartmentDTO create(Optional<Integer> id, DepartmentInputDTO inputDTO) throws DuplicateKeyException {
        DepartmentPojo pojo = new DepartmentPojo(null, inputDTO.description(), inputDTO.description());
        DepartmentRecord record = this.departmentRepository.createDepartment(pojo).join();
        if(record.getId() == null){
            throw new DataAccessException("Query execution failed.");
        }
        return new DepartmentDTO(record.getId(), record.getTitle(), record.getDescription());
    }

    public Optional<DepartmentDTO> findByID(Integer identifier) {
        Optional<DepartmentPojo>request = departmentRepository.findById(identifier).join();
        return request.map(departmentPojo -> new DepartmentDTO(departmentPojo.getId(), departmentPojo.getTitle(), departmentPojo.getDescription()));
    }

    public List<DepartmentPojo> findAll() {
        return List.of();
    }

    public DepartmentDTO batchUpsert() {
        return null;
    }

    @Override
    protected DepartmentPojo entityToPojo(Department entity) {
        return null;
    }

    @Override
    protected void updateRecordFields(Department entity, DepartmentPojo pojo) {
        DepartmentRecord record = new DepartmentRecord(pojo);

    }
}


