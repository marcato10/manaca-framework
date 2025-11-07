package com.marcato.springmarcatoerp.dataengine.core.security;

import java.util.Set;

public interface IdentityManager {
    String createUser(String email, String userName,String fullName) throws Exception;
    void updateUser();
    void deleteUser(String id) throws Exception;
    Set<String> getUserAuthorizedPermissions(String userId);
}
