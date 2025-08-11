package com.marcato.springmarcatoerp.DTO.API;

import com.marcato.springmarcatoerp.api.DomainDefinition;
import com.marcato.springmarcatoerp.domain.iEditableDomain;

import java.util.Map;
import java.util.Set;

public record AvailableDomains(Set<DomainDefinition>domains) {

}
