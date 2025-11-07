package com.marcato.springmarcatoerp.domain.department;

import com.marcato.springmarcatoerp.dataengine.core.BusinessDomain;
import com.marcato.springmarcatoerp.dataengine.starter.domain.AbstractDomainController;
import com.marcato.springmarcatoerp.jooq.tables.pojos.DepartmentPojo;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;


@Controller
@MessageMapping("departments")
public class DepartmentController extends AbstractDomainController<DepartmentPojo> {
    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);
    private final DepartmentDomain departmentDomain;
    private final DSLContext context;
    public DepartmentController(DepartmentDomain departmentDomain, DSLContext context) {
        this.departmentDomain = departmentDomain;
        this.context = context;
    }

    @Override
    protected BusinessDomain<DepartmentPojo> getDomain() {
        return this.departmentDomain;
    }

    protected DSLContext getDslContext() {
        return this.context;
    }

}
