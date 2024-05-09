package org.example.mmschulzcustomersupport.site;

import org.example.mmschulzcustomersupport.entities.UserPrincipal;

public interface UserPrincipalRepository extends GenericRepository<Long, UserPrincipal> {
    UserPrincipal getByUsername(String username);
}
