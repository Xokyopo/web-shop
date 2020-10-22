package ru.geekbrains.coursework.webshop.app.domain.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity(name = "users")
public class User extends AEntity {
    private char[] password;
    @ManyToMany
    private Set<Role> roles;

    public char[] getPassword() {
        return this.password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
