package com.marcato.springmarcatoerp.dataengine.starter;

import com.marcato.springmarcatoerp.DTO.API.AvailableDomains;
import com.marcato.springmarcatoerp.dataengine.core.DTO.DomainDefinition;
import com.marcato.springmarcatoerp.config.exceptions.DomainNotFoundException;
import com.marcato.springmarcatoerp.dataengine.core.BusinessDomain;
import com.marcato.springmarcatoerp.dataengine.core.security.PermissionManager;
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

    // 3. A "Tomada" (Interface) do PermissionManager
    private final PermissionManager permissionManager;

    /**
     * O construtor agora injeta AMBAS as dependências:
     * 1. A Lista de todos os Domínios (plug-ins da aplicação).
     * 2. A "Tomada" de Permissão (o plug-in de segurança).
     */
    @Autowired // Opcional em construtores únicos, mas bom para clareza
    public DomainManager(
            List<BusinessDomain<?>> domainsList,
            // 4. Usar @Autowired(required = false) é uma boa prática
            //    para "tomadas" de framework. Permite que o engine
            //    inicie mesmo se nenhum plug-in de segurança for fornecido.
            @Autowired(required = false) PermissionManager permissionManager
    ) {
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

    public AvailableDomains getAllowedDomains(Authentication principal) {

        Set<String> userPermissions;

        // 6. O DomainManager USA A "TOMADA"
        if (permissionManager != null) {
            // Pergunta à "tomada" quais permissões o usuário tem.
            // O DomainManager não sabe se é Auth0, AD, ou um "fake".
            userPermissions = permissionManager.getAuthorizedPermissions(principal);
        } else {
            // Se nenhum plug-in de segurança for plugado, retorna nada.
            // (Você pode logar um WARN aqui)
            userPermissions = Set.of();
        }

        // 7. A lógica de filtragem usa as permissões obtidas
        Set<DomainDefinition> domainSet = new HashSet<>();
        for (BusinessDomain<?> domain : handlerMap.values()) {

            // O próprio BusinessDomain filtra quais procedimentos
            // são permitidos com base nas permissões.
            Set<String> allowedActions = domain.getUserAllowedProcedures(userPermissions);

            if (!allowedActions.isEmpty()) {
                // Se o usuário puder fazer *pelo menos uma* ação,
                // adiciona o domínio à resposta.
                domainSet.add(domain.toDomainDefinition(allowedActions));
            }
        }

        return new AvailableDomains(domainSet);
    }
}