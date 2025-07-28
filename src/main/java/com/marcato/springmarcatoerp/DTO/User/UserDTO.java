package com.marcato.springmarcatoerp.DTO.User;

import com.marcato.springmarcatoerp.jooq.tables.pojos.UserErpPojo;
import com.marcato.springmarcatoerp.jooq.tables.records.UserErpRecord;


import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.UUID;

public record UserDTO(Integer id,
                      UUID userUuid,
                      OffsetDateTime createdAt,
                      String username,
                      String fullName
) {
    public static UUID convertOAuthSubToUUID(String sub) {

        if (sub == null || sub.trim().isEmpty()) {
            throw new IllegalArgumentException("Auth0 subject string cannot be null or empty.");
        }
        String idPart = sub;
        int pipeIndex = sub.indexOf('|');
        if (pipeIndex != -1 && pipeIndex + 1 < sub.length()) {
            idPart = sub.substring(pipeIndex + 1);
        }

        return UUID.nameUUIDFromBytes(idPart.getBytes(StandardCharsets.UTF_8));
    }
    public static UserDTO fromPojo(UserErpPojo pojo) {
        if (pojo == null) {
            return null;
        }

        return new UserDTO(
                pojo.getId(),
                pojo.getUserUuid(),
                pojo.getCreatedAt(),
                pojo.getUsername(),
                pojo.getFullName()
        );
    }
    public static UserDTO fromRecord(UserErpRecord record) {
        if (record == null) {
            return null;
        }
        return new UserDTO(
                record.getId(),
                record.getUserUuid(),
                record.getCreatedAt(),
                record.getUsername(),
                record.getFullName()
        );
    }
}
