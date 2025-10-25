package com.marcato.springmarcatoerp.dataengine.core.security;

import java.util.Set;

public interface IdentityAccessManagerProvider {

    Set<String> getRolePermissions(String roleId);
}
