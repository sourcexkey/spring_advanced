package com.epam.cdp.model;

/**
 * Created by maksym_govorischev on 14/03/14.
 */
public interface User extends Entity {
    String getName();
    void setName(String name);

    /**
     * User email. UNIQUE.
     * @return User email.
     */
    String getEmail();
    void setEmail(String email);
    User clone();
}
