package ru.geekbrains.coursework.webshop.app.domain.entities;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "products")
public class Product extends AEntity {
    @ManyToMany
    private Set<Category> categories;
    @ManyToOne
    private Brand brand;
    @ElementCollection
    private Set<String> imagesUrls;
    private long price;

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

    public Set<String> getImagesUrls() {
        return imagesUrls;
    }

    public void setImagesUrls(Set<String> imagesUrls) {
        this.imagesUrls = imagesUrls;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @PreRemove
    public void executePreRemove() {
        brand.getProducts().remove(this);
    }
}
