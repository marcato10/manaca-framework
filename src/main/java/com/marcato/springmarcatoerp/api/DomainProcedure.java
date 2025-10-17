package com.marcato.springmarcatoerp.api;

import com.fasterxml.jackson.databind.JsonNode;

public record DomainProcedure(String domainKey,
                              String instanceId,
                              String action,
                              JsonNode actionParams
) {
}
