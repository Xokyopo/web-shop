package ru.geekbrains.coursework.webshop.app.domain.entities;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;

@Entity(name = "products")
public class Product extends AEntity {
    @ManyToMany(
            mappedBy = "products"
    )
    private Set<Category> categories;
    @ManyToOne
    private Brand brand;

    public Product() {
    }

    public Set<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Brand getBrand() {
        return this.brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    @PreRemove
    public void preRemoveAction() {
        this.categories.forEach((product) -> {
            product.getProducts().remove(this);
        });
    }
}
