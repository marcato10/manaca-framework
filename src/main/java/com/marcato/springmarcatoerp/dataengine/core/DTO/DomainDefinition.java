package com.marcato.springmarcatoerp.dataengine.core.DTO;

import java.util.List;
import java.util.Set;

public record DomainDefinition(String key, List<FieldDefinition>fields, Set<String>allowedActions) {
}
