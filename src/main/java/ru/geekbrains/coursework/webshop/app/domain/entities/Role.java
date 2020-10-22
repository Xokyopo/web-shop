package ru.geekbrains.coursework.webshop.app.domain.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;
import java.util.Set;

@Entity(name = "roles")
public class Role extends AEntity {
    private String description;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<User> getUsers() {
        return this.users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @PreRemove
    public void preRemoveAction() {
        this.users.forEach((role) -> {
            role.getRoles().remove(this);
        });
    }
}
