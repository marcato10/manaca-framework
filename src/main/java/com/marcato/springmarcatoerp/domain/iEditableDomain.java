package com.marcato.springmarcatoerp.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.marcato.springmarcatoerp.api.DomainDefinition;
import com.marcato.springmarcatoerp.api.FieldDefinition;
import com.marcato.springmarcatoerp.domain.rpc.actions.iAction;
import com.marcato.springmarcatoerp.domain.rpc.procedures.ApplicationProcedure;
import org.jooq.*;
import org.jooq.Record;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

//P: POJO
//R: Record
public interface iEditableDomain<P, R extends Record> {
    String getKey();
    Table<R> getTable();
    default List<FieldDefinition> getDomainFields() {
        return Arrays.stream(getTable().fields())
                .map(field -> new FieldDefinition(field.getName(), field.getDataType().getTypeName()))
                .collect(Collectors.toList());
    }
    default DomainDefinition toDomainDefinition(Set<String> permissions) {
        return new DomainDefinition(getKey(), getDomainFields(), permissions);
    }
    //PROCEDURES
    P create(P pojo);
    P read(String id);
    P update(String id, P pojo);
    P patch(String id, JsonNode node);
    String delete(String id);
    List<P> getList();
    //APPLICATION PROCEDURES
    default Set<String> getApplicationsProcedureName(){
        return Set.of();
    }

    //BUSINESS FOCUSED ACTIONS
    default Set<String> getBusinessActionsName() {
        return Set.of();
    }

    default Set<String> getUserAllowedProcedures(Set<String> userPermissions) {
        Map<String, String> permissionsMap = getProceduresByPermission();
        return permissionsMap.entrySet().stream().filter(entry -> userPermissions.contains(entry.getKey()))
                .map(Map.Entry::getValue).collect(Collectors.toSet());
    }
    default Map<String, String> getProceduresByPermission() {
        return Map.of(
                "read:" + getKey(), "view",
                "edit:" + getKey(), "edit"
        );
    }
}
