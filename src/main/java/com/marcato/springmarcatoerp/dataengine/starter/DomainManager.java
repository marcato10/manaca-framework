package com.marcato.springmarcatoerp.dataengine.starter;

import com.marcato.springmarcatoerp.DTO.API.AvailableDomains;
import com.marcato.springmarcatoerp.dataengine.core.DTO.DomainDefinition;
import com.marcato.springmarcatoerp.config.exceptions.DomainNotFoundException;
import com.marcato.springmarcatoerp.dataengine.core.BusinessDomain;
import com.marcato.springmarcatoerp.dataengine.core.security.PermissionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.marcato.springmarcatoerp.config.security.CustomTokenAuthorities.PERMISSIONS_CLAIM;
@Service
public class DomainManager {

    private final Map<String, BusinessDomain<?>> handlerMap;
    private final PermissionManager permissionManager;

    @Autowired
    public DomainManager(
            List<BusinessDomain<?>> domainsList,
            @Autowired(required = false) PermissionManager permissionManager
    )
    {
        this.handlerMap = domainsList.stream().collect(Collectors.toMap(BusinessDomain::getKey, Function.identity()));
        this.permissionManager = permissionManager; // 5. Atribui o manager injetado
    }

    public BusinessDomain<?> getHandler(String key){
        BusinessDomain<?> handler = handlerMap.get(key);
        if(handler == null){
            throw new DomainNotFoundException();
        }
        return handler;
    }

    public AvailableDomains getAllowedDomains(Jwt principal) {

        Set<String> userPermissions = Set.of();
        if (permissionManager != null) {
            userPermissions = permissionManager.getAuthorizedPermissions(principal);
        }

        Set<DomainDefinition> domainSet = new HashSet<>();
        for (BusinessDomain<?> domain : handlerMap.values()) {
            Set<String> allowedActions = domain.getUserAllowedProcedures(userPermissions);
            if (!allowedActions.isEmpty()) {
                domainSet.add(domain.toDomainDefinition(allowedActions));
            }
        }

        return new AvailableDomains(domainSet);
    }
}