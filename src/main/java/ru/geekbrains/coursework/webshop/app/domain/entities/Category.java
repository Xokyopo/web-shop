package ru.geekbrains.coursework.webshop.app.domain.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;
import java.util.Set;

@Entity(name = "categories")
public class Category extends AEntity {
    @ManyToMany(mappedBy = "categories")
    private Set<Product> products;

    public Set<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @PreRemove
    public void preRemoveAction() {
        this.products.forEach((product) -> {
            product.getCategories().remove(this);
        });
    }
}
