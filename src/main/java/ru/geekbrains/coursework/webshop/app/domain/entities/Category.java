package ru.geekbrains.coursework.webshop.app.domain.entities;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity(name = "categories")
public class Category extends AEntity {
    @ManyToMany
    private Set<Product> products;

    public Category() {
    }

    public Set<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
