package com.marcato.springmarcatoerp.service;

import org.springframework.dao.DuplicateKeyException;

import java.util.List;
import java.util.Optional;

public interface iService<T,I,O,ID> {
    O create(Optional<ID> id,I inputDTO) throws DuplicateKeyException;
    Optional<O> findByID(ID identifier);

    List<T> findAll();
    O batchUpsert();

}
