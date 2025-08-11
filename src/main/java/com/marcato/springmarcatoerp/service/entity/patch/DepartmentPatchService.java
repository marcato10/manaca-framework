package com.marcato.springmarcatoerp.service.entity.patch;

import com.marcato.springmarcatoerp.jooq.tables.pojos.DepartmentPojo;
import com.marcato.springmarcatoerp.jooq.tables.records.DepartmentRecord;
import com.marcato.springmarcatoerp.service.jsonpatch.JsonPatchHandler;
import org.jooq.Configuration;
import org.springframework.stereotype.Service;

@Service
public class DepartmentPatchService extends AbstractEntityPatchService<DepartmentRecord, DepartmentPojo> {
    protected DepartmentPatchService(JsonPatchHandler patchHandler) {
        super(patchHandler);
    }

    @Override
    protected DepartmentPojo entityRecordToPojo(DepartmentRecord entity) {
        return entity.into(DepartmentPojo.class);
    }

    @Override
    protected void updateRecordFields(DepartmentRecord entity, DepartmentPojo patchedPojo) {
        entity.from(patchedPojo);
    }
}
