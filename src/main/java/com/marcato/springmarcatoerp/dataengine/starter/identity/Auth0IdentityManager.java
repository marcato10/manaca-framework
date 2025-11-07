package com.marcato.springmarcatoerp.dataengine.starter.identity;

import com.marcato.springmarcatoerp.dataengine.core.security.PermissionManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class Auth0IdentityManager implements PermissionManager {
    private static final String PERMISSIONS_CLAIM = "https://marcato.erp.com/permissions";
    @Override
    public Set<String> getAuthorizedPermissions(Jwt principal) {
        List<String> permissions = principal.getClaimAsStringList(PERMISSIONS_CLAIM);

        if (permissions == null) {
            return Set.of();
        }
        return new HashSet<>(permissions);
    }

    @Override
    public void providePermission(String permissionName, String description) throws Exception {

    }

    @Override
    public void createRole(String roleName, String description) throws Exception {

    }

    @Override
    public void deleteRole(String roleName) throws Exception {

    }

    @Override
    public void assignPermissionToRole(String permissionName, String roleName) throws Exception {

    }

    @Override
    public void removePermissionFromRole(String permissionName, String role) throws Exception {

    }

    @Override
    public Set<String> getRolesFromUser(String userId) {
        return Set.of();
    }

    @Override
    public Set<String> getRolePermissions(String roleName) {
        return Set.of();
    }



    @Override
    public void assignRoleToUser(String userId, String roleName) throws Exception {

    }

    @Override
    public void removeRoleFromUser(String userId, String roleName) throws Exception {

    }
}
