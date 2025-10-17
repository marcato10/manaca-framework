package com.marcato.springmarcatoerp.domain.rpc.actions;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DomainActions {
    Set<String> domain = Set.of("view", "edit");
    Set<String> application = Set.of(
            "rowUpdate","rowCreate","rowDelete"
    );
    Set<String> business = new HashSet<>();

}
