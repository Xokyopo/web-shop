package ru.geekbrains.coursework.webshop.app.domain.entities;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity(name = "roles")
public class Role extends AEntity {
    private String description;
    @ManyToMany
    private Set<User> users;

    public Role() {
    }

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
}
