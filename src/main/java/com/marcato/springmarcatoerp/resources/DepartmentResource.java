package com.marcato.springmarcatoerp.resources;

import com.marcato.springmarcatoerp.DTO.Department.DepartmentDTO;
import com.marcato.springmarcatoerp.DTO.Department.DepartmentInputDTO;
import com.marcato.springmarcatoerp.entity.tables.daos.DepartmentDao;
import com.marcato.springmarcatoerp.entity.tables.pojos.DepartmentPojo;
import com.marcato.springmarcatoerp.service.DepartmentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@PreAuthorize("hasAuthority('read:department')")
public class DepartmentResource {
    Logger logger = LoggerFactory.getLogger(DepartmentResource.class);
    private final DepartmentService departmentService;

    public DepartmentResource(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @PostMapping
    @PreAuthorize("hasAuthority('create:department')")
    public ResponseEntity<String>createDepartment(@Valid @RequestBody DepartmentInputDTO departDTO){
        try{
            DepartmentDao departDao = new DepartmentDao();
            String title = departDTO.title();
            if(!departDao.fetchByTitle(title).isEmpty()){
                return new ResponseEntity<>("Department already registered", HttpStatusCode.valueOf(409));
            }
            DepartmentPojo departmentPojo = new DepartmentPojo();
            departmentPojo.setTitle(departDTO.title());
            departmentPojo.setDescription(departDTO.description());
            logger.info("Trying to Insert Department");
            if(departmentService.createDepartment(departmentPojo).get() > 0){
                logger.info("Department Inserted With Success");
                return new ResponseEntity<>("Department Registration was a success.",HttpStatusCode.valueOf(201));
            }
            logger.warn("Department was not registered");
            return new ResponseEntity<>("Department Registration not happened.",HttpStatusCode.valueOf(500));
        }
        catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>("Error: " + e.getMessage(),HttpStatusCode.valueOf(500));
        }
    }

    @PostMapping("/batch-insert")
    @PreAuthorize("hasAuthority('update:department')")
    public ResponseEntity<?>updateDepartments(@Valid @RequestBody List<DepartmentInputDTO> departs){
        try {
            logger.info("Tryint to batch upsert");

            List<DepartmentInputDTO> toCreate = departs.stream()
                    .filter(dto -> dto.id() == null)
                    .toList();

            List<DepartmentInputDTO> toUpdate = departs.stream()
                    .filter(dto -> dto.id() != null)
                    .toList();
            List<DepartmentDTO> result = departmentService.batchUpsert(toCreate, toUpdate).get();
            logger.info("Batch upsert completed with success.");
            return new ResponseEntity<>(result, HttpStatusCode.valueOf(200));

        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage(), e);
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatusCode.valueOf(500));
        }
    }

    @GetMapping
    public ResponseEntity<?>fetchDepartments(){
        try{
            List<DepartmentDTO>departments = departmentService.getAllDepartments().get();
            return new ResponseEntity<>(departments, HttpStatusCode.valueOf(200));
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatusCode.valueOf(500));
        }
    }
}
