package com.marcato.springmarcatoerp.service.entity.patch;

import com.fasterxml.jackson.databind.JsonNode;
import com.marcato.springmarcatoerp.service.jsonpatch.JsonPatchHandler;
import com.marcato.springmarcatoerp.service.jsonpatch.exception.JsonPatchInternalServerError;
import org.springframework.stereotype.Service;

//R: entity Record
//P: pojo
//ID: Identifier
@Service
public abstract class AbstractEntityPatchService<R,P> {
    protected final JsonPatchHandler patchHandler;

    protected AbstractEntityPatchService(JsonPatchHandler patchHandler) {
        this.patchHandler = patchHandler;
    }

    public R patchEntity(R entity, JsonNode patchNode) throws JsonPatchInternalServerError {
        P entityPojo = entityRecordToPojo(entity);

        P updatedEntityPojo = this.applyPatchToPojo(patchNode,entityPojo);
        updateRecordFields(entity,updatedEntityPojo);
        return entity;
    }

    protected P applyPatchToPojo(JsonNode patch, P pojo) throws JsonPatchInternalServerError {
        return this.patchHandler.applyPatch(patch,pojo, (Class<P>) pojo.getClass());
    }

    protected abstract P entityRecordToPojo(R entity);
    protected abstract void updateRecordFields(R entity, P pojo);
}
