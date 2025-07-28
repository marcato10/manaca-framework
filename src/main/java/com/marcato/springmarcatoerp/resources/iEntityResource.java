package com.marcato.springmarcatoerp.resources;

import com.fasterxml.jackson.databind.JsonNode;
import com.marcato.springmarcatoerp.service.jsonpatch.JsonPatchUpdate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

import static com.marcato.springmarcatoerp.config.Constants.APPLICATION_JSON_PATCH_VALUE;

@RequestMapping("/entity")
public interface iEntityResource<O> {
    @Transactional
    @PatchMapping(
            path = "/{id}",
            consumes = APPLICATION_JSON_PATCH_VALUE)
    @JsonPatchUpdate(allowedPaths = {})
    ResponseEntity<O> updateEntity(@PathVariable("id") String id, @RequestBody JsonNode patch);
}
