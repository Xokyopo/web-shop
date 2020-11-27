package ru.geekbrains.coursework.webshop.app.domain.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ru.geekbrains.coursework.webshop.app.domain.entities.json.ProductDeserializer;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "products")
@JsonDeserialize(using = ProductDeserializer.class)
public class Product extends AEntity {
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Category> categories;
    @ManyToOne
    @JsonManagedReference
    private Brand brand;
    @ElementCollection(fetch = FetchType.EAGER)
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
