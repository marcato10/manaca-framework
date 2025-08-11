package com.marcato.springmarcatoerp.config.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Domain not found.", code = org.springframework.http.HttpStatus.NOT_FOUND)
public class DomainNotFoundException extends RuntimeException{
}
