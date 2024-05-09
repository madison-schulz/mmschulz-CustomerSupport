package org.example.mmschulzcustomersupport.site;

import org.example.mmschulzcustomersupport.entities.UserPrincipal;

public interface AuthenticationService {
    UserPrincipal authenticate(String username, String password);
    void saveUser(UserPrincipal principal, String newPassword);

}
