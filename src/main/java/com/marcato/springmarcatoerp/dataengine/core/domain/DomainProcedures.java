package com.marcato.springmarcatoerp.dataengine.core.domain;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DomainProcedures<P> {
    P create(P pojo);
    P read(String id);
    P update(String id, P pojo);
    String delete(String id);
    List<P> getList();
    default Set<String> getProcedures(){
        return Set.of("create", "read", "update", "patch", "delete", "list");
    }
}
