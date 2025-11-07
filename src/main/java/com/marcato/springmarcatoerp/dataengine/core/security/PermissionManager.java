package com.marcato.springmarcatoerp.dataengine.core.security;

import com.marcato.springmarcatoerp.dataengine.core.BusinessDomain;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Set;

public interface PermissionManager {
    /**
     * Ensures that a permission exists in the provider. Must be idempotent.
     * @param permissionName Unique name (e.g., "departments:create")
     * @param description Description
     * @throws Exception If it fails
     */
    void providePermission(String permissionName, String description) throws Exception;

    default void actionToPermission(BusinessDomain domain) throws Exception{
        String domainKey = domain.getKey();

    }

    /**
     * Creates a new role in the provider.
     * @param roleName Role name (e.g., "Admin").
     * @param description Description.
     * @throws Exception If the role already exists.
     */
    void createRole(String roleName, String description) throws Exception;
    void deleteRole(String roleName) throws Exception;
    /**
     * Assigns a (previously provisioned) permission to a role.
     * @param permissionName Permission name (e.g., "departments:create").
     * @param roleName Role name (e.g., "Admin").
     * @throws Exception If the permission or role does not exist.
     */
    void assignPermissionToRole(String permissionName, String roleName) throws Exception;

    void removePermissionFromRole(String permissionName, String role) throws Exception;



    /**
     * Gets all roles from one user.
     * @param userId The user's ID in the provider.
     * @return A Set containing the names of the roles.
     */
    Set<String> getRolesFromUser(String userId);
    /**
     * Gets all roles from one user.
     * @param roleName Role name (e.g., "Admin").
     * @return A Set containing the permissions of the roles.
     */
    Set<String> getRolePermissions(String roleName);
    Set<String> getAuthorizedPermissions(Jwt principal);
    /**
     * Assigns a role to a user.
     * @param userId The user's ID in the provider (e.g., "auth0|123").
     * @param roleName Role name (e.g., "Admin").
     * @throws Exception If the user or role does not exist.
     */
    void assignRoleToUser(String userId, String roleName) throws Exception;
    /**
     * Removes a role from one user.
     * @param userId The user's ID in the provider.
     * @param roleName Role name.
     * @throws Exception If the assignment doesn't exist or fails.
     */
    void removeRoleFromUser(String userId, String roleName) throws Exception;


}
