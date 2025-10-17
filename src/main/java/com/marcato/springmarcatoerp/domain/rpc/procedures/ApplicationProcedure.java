package com.marcato.springmarcatoerp.domain.rpc.procedures;

import com.fasterxml.jackson.databind.JsonNode;

public interface ApplicationProcedure<P,R extends Record> {
    String getName();
    P execute(P pojo, JsonNode payload,R record);
}
