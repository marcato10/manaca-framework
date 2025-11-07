package com.marcato.springmarcatoerp.dataengine.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.marcato.springmarcatoerp.dataengine.core.DTO.DomainDefinition;
import com.marcato.springmarcatoerp.dataengine.core.DTO.FieldDefinition;
import com.marcato.springmarcatoerp.dataengine.core.domain.DomainProcedures;
import org.jooq.*;
import org.jooq.Record;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

//P: POJO
//Classe de Descoberta.
public interface BusinessDomain<P> {
    String getKey();
    DomainProcedures<P> getDomainProcedures();
    List<FieldDefinition> getDomainFields();
    default Set<String> getBusinessActions() {
        return Set.of();
    }

    Map<String, String> getProceduresByPermission();
    default Set<String> getUserAllowedProcedures(Set<String> userPermissions) {
        Map<String, String> permissionsMap = getProceduresByPermission();
        return permissionsMap.entrySet().stream()
                .filter(entry -> userPermissions.contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());
    }

    default DomainDefinition toDomainDefinition(Set<String> allowedActions) {
        return new DomainDefinition(getKey(), getDomainFields(), allowedActions);
    }
}
