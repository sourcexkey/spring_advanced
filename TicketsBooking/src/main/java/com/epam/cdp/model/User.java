package com.epam.cdp.model;

import com.epam.cdp.security.Roles;

import java.util.List;

/**
 * Created by maksym_govorischev on 14/03/14.
 */
public interface User extends Entity {

    String password = null;
    List<Roles> roles = null;

    String getName();

    void setName(String name);

    /**
     * User email. UNIQUE.
     *
     * @return User email.
     */
    String getEmail();

    void setEmail(String email);

    String getPassword();

    void setPassword(String password);

    List<Roles> getRoles();

    void setRoles(List<Roles> roles);

    User clone();
}
