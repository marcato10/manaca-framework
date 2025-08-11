package com.marcato.springmarcatoerp.domain;

import com.marcato.springmarcatoerp.api.DomainDefinition;
import com.marcato.springmarcatoerp.api.FieldDefinition;
import org.jooq.*;
import org.jooq.Record;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

//P: POJO
public interface iEditableDomain<P,R extends Record> {
    String getKey();
    Table<R> getTable();
    default Set<String> getUserAllowedActions(Set<String>userPermissions){
        Map<String,String>permissionsMap = getActionPermissions();
        return permissionsMap.entrySet().stream().filter(entry->userPermissions.contains(entry.getKey()))
                .map(Map.Entry::getValue).collect(Collectors.toSet());
    }

    default Map<String,String> getActionPermissions(){
        return Map.of(
                "read:"+getKey(),"view",
                "edit:"+getKey(), "edit"
        );
    }
    default List<FieldDefinition> getDomainFields(){
        return Arrays.stream(getTable().fields())
                .map(field -> new FieldDefinition(field.getName(), field.getDataType().getTypeName()))
                .collect(Collectors.toList());
    }

    default DomainDefinition toDomainDefinition(Set<String>permissions){
        return new DomainDefinition(getKey(),getDomainFields(),permissions);
    }

    void validate(P pojo);
    P save(P pojo, DSLContext context);
    P create(P pojo);
    void delete(String id);
    void handleRealtimeUpdates(WebSocketSession session, TextMessage message, String topic, String key);
}
