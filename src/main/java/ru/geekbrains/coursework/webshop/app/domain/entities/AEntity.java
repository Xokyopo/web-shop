package ru.geekbrains.coursework.webshop.app.domain.entities;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public abstract class AEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;
    @NotNull
    private String name;

    public AEntity() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(Object comparedObject) {
        if (this == comparedObject) {
            return true;
        } else if (comparedObject != null && this.getClass() == comparedObject.getClass()) {
            AEntity aEntity = (AEntity) comparedObject;
            return this.getId() == aEntity.getId() && this.getName().equals(aEntity.getName());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(this.getId(), this.getName());
    }

    public String toString() {
        return "Entity{id=" + this.id + ", name='" + this.name + '\'' + '}';
    }
}
