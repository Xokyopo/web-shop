package ru.geekbrains.coursework.webshop.app.domain.entities;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity(name = "brands")
public class Brand extends AEntity {
    @OneToMany
    private Set<Product> products;

    public Brand() {
    }

    public Set<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
