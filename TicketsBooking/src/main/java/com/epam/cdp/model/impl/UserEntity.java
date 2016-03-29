package com.epam.cdp.model.impl;

import com.epam.cdp.model.User;
import com.epam.cdp.security.Roles;

import java.util.List;

public class UserEntity implements User {

    private long id;
    private String name;
    private String email;
    String password;
    List<Roles> roles;

    public UserEntity() {
    }

    public UserEntity(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public List<Roles> getRoles() {
        return roles;
    }

    @Override
    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserEntity that = (UserEntity) o;

        if (id != that.id) {
            return false;
        }
        return !(name != null ? !name.equals(that.name) : that.name != null) && !(email != null
                                                                                  ? !email
                .equals(that.email) : that.email != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public User clone() {
        UserEntity newUser = new UserEntity();
        newUser.id = this.id;
        newUser.name = this.name;
        newUser.email = this.email;
        return newUser;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", email='" + email + '\'' +
               '}';
    }
}
