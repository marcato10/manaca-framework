package com.marcato.springmarcatoerp.service.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.marcato.springmarcatoerp.jooq.tables.records.DepartmentRecord;
import com.marcato.springmarcatoerp.service.jsonpatch.JsonPatchHandler;
import com.marcato.springmarcatoerp.service.jsonpatch.exception.JsonPatchInternalServerError;

//E: entity
//P: pojo
//R: record
public abstract class AbstractEntityPatchService<E,P> {
    protected final JsonPatchHandler patchHandler;

    protected AbstractEntityPatchService(JsonPatchHandler patchHandler) {
        this.patchHandler = patchHandler;
    }

    public E patchEntity(E entity, JsonNode patchNode) throws JsonPatchInternalServerError {
        P pojo = this.entityToPojo(entity);
        E patchedEntity = patchHandler.applyPatch(patchNode,entity,(Class<E>)entity.getClass());
        updateRecordFields(entity,pojo);

        return patchedEntity;
    }

    protected abstract P entityToPojo(E entity);
    protected abstract void updateRecordFields(E entity, P pojo);
}
