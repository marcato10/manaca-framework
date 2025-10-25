package com.marcato.springmarcatoerp.DTO.API;

import com.marcato.springmarcatoerp.dataengine.core.DTO.DomainDefinition;

import java.util.Set;

public record AvailableDomains(Set<DomainDefinition>domains) {

}
