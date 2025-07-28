package com.marcato.springmarcatoerp.resources;

import com.fasterxml.jackson.databind.JsonNode;
import com.marcato.springmarcatoerp.DTO.Department.DepartmentDTO;
import com.marcato.springmarcatoerp.DTO.Department.DepartmentInputDTO;
import com.marcato.springmarcatoerp.assembler.DepartmentModelAssembler;
import com.marcato.springmarcatoerp.jooq.tables.pojos.DepartmentPojo;
import com.marcato.springmarcatoerp.service.entity.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/departments")
@PreAuthorize("hasAuthority('read:department')")
public class DepartmentResource implements iEntityResource<DepartmentDTO>{
    Logger logger = LoggerFactory.getLogger(DepartmentResource.class);
    private final DepartmentService departmentService;
    private final DepartmentModelAssembler departmentAssembler;
    public DepartmentResource(DepartmentService departmentService, DepartmentModelAssembler departmentAssembler) {

        this.departmentService = departmentService;
        this.departmentAssembler = departmentAssembler;
    }

    @GetMapping("{id}")
    public ResponseEntity<EntityModel<DepartmentPojo>> retrieveDepartmentById(@PathVariable Integer id){
        Optional<DepartmentDTO>departmentDTO = departmentService.findByID(id);
        if(departmentDTO.isPresent()){
            DepartmentPojo depPojo = DepartmentDTO.toPojo(departmentDTO.get());
            EntityModel<DepartmentPojo>depModel = departmentAssembler.toModel(depPojo);
            return ResponseEntity.ok(depModel);
        }
        return ResponseEntity.notFound().build();
    }
    @PreAuthorize("hasAnyAuthority('write:department')")
    @PostMapping
    public ResponseEntity<EntityModel<DepartmentPojo>> register(@RequestBody DepartmentInputDTO depInput){
        DepartmentPojo depPojo = new DepartmentPojo();
        EntityModel<DepartmentPojo>depModel = departmentAssembler.toModel(depPojo);
        return ResponseEntity.ok(depModel);
    }

    @Override
    public ResponseEntity<DepartmentDTO> updateEntity(String id, JsonNode patch) {

        return null;
    }
}
