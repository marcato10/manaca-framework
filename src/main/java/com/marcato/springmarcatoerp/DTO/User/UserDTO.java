package com.marcato.springmarcatoerp.DTO.User;

import com.marcato.springmarcatoerp.jooq.tables.pojos.UsererpPojo;
import com.marcato.springmarcatoerp.jooq.tables.records.UsererpRecord;

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

    public static UserDTO fromPojo(UsererpPojo pojo) {
        if (pojo == null) {
            return null;
        }

        return new UserDTO(
                pojo.getId(),
                pojo.getUserUuid(),
                pojo.getCreatedat(), // Note que o getter do POJO é usado aqui
                pojo.getUsername(),
                pojo.getFullName()
        );
    }
    public static UserDTO fromRecord(UsererpRecord record) {
        if (record == null) {
            return null;
        }
        return new UserDTO(
                record.getId(),
                record.getUserUuid(),
                record.getCreatedat(),
                record.getUsername(),
                record.getFullName()
        );
    }
}
