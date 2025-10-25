package com.marcato.springmarcatoerp.domain;

import com.marcato.springmarcatoerp.dataengine.core.iEditableDomain;
import com.marcato.springmarcatoerp.dataengine.starter.domain.AbstractDomainController;
import com.marcato.springmarcatoerp.jooq.tables.pojos.DepartmentPojo;
import com.marcato.springmarcatoerp.jooq.tables.records.DepartmentRecord;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;


@Controller
@MessageMapping("departments")
public class DepartmentController extends AbstractDomainController<DepartmentPojo, DepartmentRecord> {
    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);
    private final DepartmentDomain departmentDomain;
    private final DSLContext context;
    public DepartmentController(DepartmentDomain departmentDomain, DSLContext context) {
        this.departmentDomain = departmentDomain;
        this.context = context;
    }

    @Override
    protected iEditableDomain<DepartmentPojo, DepartmentRecord> getDomain() {
        return this.departmentDomain;
    }

    @Override
    protected DSLContext getDslContext() {
        return this.context;
    }

}
