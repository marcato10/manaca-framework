package com.marcato.springmarcatoerp.service;

import com.marcato.springmarcatoerp.DTO.Department.DepartmentDTO;
import com.marcato.springmarcatoerp.DTO.Department.DepartmentInputDTO;
import com.marcato.springmarcatoerp.entity.tables.Department;
import com.marcato.springmarcatoerp.entity.tables.pojos.DepartmentPojo;
import com.marcato.springmarcatoerp.entity.tables.records.DepartmentRecord;
import org.jooq.DSLContext;
import org.jooq.InsertQuery;
import org.jooq.UpdateQuery;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@Service
public class DepartmentService {

}
