package com.marcato.springmarcatoerp.service.jsonpatch;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonPatch;
import com.marcato.springmarcatoerp.service.jsonpatch.exception.JsonPatchInternalServerError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class JsonPatchHandler {
    Logger logger = LoggerFactory.getLogger(JsonPatchHandler.class);
    private final ObjectMapper mapper;
    public JsonPatchHandler(ObjectMapper objectMapper) {
        this.mapper = objectMapper;
    }
    
    private <T> T convertJsonToPojo(JsonNode json, Class<T> pojo) throws JsonPatchInternalServerError {
        try{
            return this.mapper.convertValue(json,pojo);
        }
        catch (IllegalArgumentException e){
            String messageError = e.getMessage() + pojo.getSimpleName();
            logger.error(messageError,e);
            throw new JsonPatchInternalServerError(messageError,e);
        }
    }

    private <T> JsonNode convertToJsonNode(T entityTarget){
        return mapper.convertValue(entityTarget, JsonNode.class);
    }

    public <T> T applyPatch(JsonNode patchNode,T entity, Class<T> entityClass) throws JsonPatchInternalServerError {
        JsonNode node = this.convertToJsonNode(entity);
        JsonNode updatedNode = JsonPatch.apply(patchNode,node);

        return convertJsonToPojo(updatedNode,entityClass);
    }
}
