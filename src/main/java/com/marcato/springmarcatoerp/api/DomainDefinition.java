package com.marcato.springmarcatoerp.api;

import java.util.List;
import java.util.Set;

public record DomainDefinition(String key, List<FieldDefinition>fields, Set<String>allowedActions) {
}
