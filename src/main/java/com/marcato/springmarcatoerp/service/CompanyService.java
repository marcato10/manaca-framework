package com.marcato.springmarcatoerp.service;

import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    private final DSLContext create;

    public CompanyService(DSLContext context){
        this.create = context;
    }


}
