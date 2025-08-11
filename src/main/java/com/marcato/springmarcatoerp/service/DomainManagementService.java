package com.marcato.springmarcatoerp.service;

import com.marcato.springmarcatoerp.DTO.API.AvailableDomains;
import com.marcato.springmarcatoerp.api.DomainDefinition;
import com.marcato.springmarcatoerp.config.exceptions.DomainNotFoundException;
import com.marcato.springmarcatoerp.domain.iEditableDomain;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.marcato.springmarcatoerp.security.CustomTokenAuthorities.PERMISSIONS_CLAIM;

@Service
public class DomainManagementService {
    private final Map<String, iEditableDomain<?,?>>handlerMap;
    public DomainManagementService(List<iEditableDomain<?,?>> domainsList) {
        this.handlerMap = domainsList.stream().collect(Collectors.toMap(iEditableDomain::getKey, Function.identity()));
    }

    public iEditableDomain<?,?> getHandler(String key){
        iEditableDomain<?,?> handler = handlerMap.get(key);
        if(handler == null){
            throw new DomainNotFoundException();
        }
        return handler;
    }
    private Set<String>extractUserPermissions(Jwt principal){
        List<String>permissions = principal.getClaimAsStringList(PERMISSIONS_CLAIM);
        if(permissions == null){
            permissions = List.of();
        }
        System.out.println(permissions);
        return new HashSet<>(permissions);
    }

    public AvailableDomains getAllowedDomains(Jwt principal){
        Set<String>userPermissions = extractUserPermissions(principal);
        Set<DomainDefinition>domainSet = new HashSet<>();
        for(iEditableDomain<?,?>domain : handlerMap.values()){
            if(userPermissions.contains("read:"+domain.getKey())){
                System.out.println(domain.getDomainFields());
                domainSet.add(domain.toDomainDefinition(domain.getUserAllowedActions(userPermissions)));
            }
        }

        return new AvailableDomains(domainSet);
    }
}
