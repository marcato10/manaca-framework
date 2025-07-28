package com.marcato.springmarcatoerp.security;
import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomTokenAuthorities implements Converter<Jwt, Collection<GrantedAuthority>> {
    private static final String GROUPS_CLAIM_NAME = "https://marcato.erp.com/groups";
    private static final String PERMISSIONS_CLAIM = "permissions";
    private static final String READ_PERMISSIONS_CLAIM = "read_permissions";

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        List<String>groups = source.getClaimAsStringList(GROUPS_CLAIM_NAME);
        if(groups == null){
            groups = List.of();
        }

        List<String>permissions = source.getClaimAsStringList(PERMISSIONS_CLAIM);
        if(permissions == null){
            permissions = List.of();
        }
        return Stream.concat(groups.stream(), permissions.stream())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
