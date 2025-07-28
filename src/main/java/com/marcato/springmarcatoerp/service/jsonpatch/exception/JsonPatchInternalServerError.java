package com.marcato.springmarcatoerp.service.jsonpatch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        reason = "An error occurred during patch operation.",
        code = HttpStatus.INTERNAL_SERVER_ERROR
)
public class JsonPatchInternalServerError extends Throwable{
    public JsonPatchInternalServerError(String message,IllegalArgumentException e){
        super(message,e);
    }
}
