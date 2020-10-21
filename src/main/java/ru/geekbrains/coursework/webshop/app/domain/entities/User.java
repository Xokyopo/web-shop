package ru.geekbrains.coursework.webshop.app.domain.entities;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;

@Entity(name = "users")
public class User extends AEntity {
    private char[] password;
    @ManyToMany(
            mappedBy = "users"
    )
    private Set<Role> roles;

    public User() {
    }

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

    @PreRemove
    public void preRemoveAction() {
        this.roles.forEach((role) -> {
            role.getUsers().remove(this);
        });
    }
}
