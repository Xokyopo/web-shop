package ru.geekbrains.coursework.webshop.app.domain.entities;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import java.util.Set;

@Entity(name = "brands")
public class Brand extends AEntity {
    @OneToMany(mappedBy = "brand")
    private Set<Product> products;

    public Set<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @PreRemove
    public void executePreRemove() {
        this.products.forEach(product -> product.setBrand(null));
    }
}
