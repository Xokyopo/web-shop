package ru.geekbrains.coursework.webshop.app.domain.entities;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Entity(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long dateTime;
    private long count;
    private long price;
    @Column(length = 5000, name = "products_as_json")
    @Basic(fetch = FetchType.LAZY)
    private String productsAsJSON;

    public Sale() {
    }

    public Sale(long dateTime, long count, long price, String productsAsJSON) {
        this.dateTime = dateTime;
        this.count = count;
        this.price = price;
        this.productsAsJSON = productsAsJSON;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDateTime() {
        return dateTime;
    }

    public String getDateTimeAsString() {
        return LocalDateTime
                .ofInstant(Instant.ofEpochMilli(dateTime), ZoneOffset.systemDefault())
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getProductsAsJSON() {
        return productsAsJSON;
    }

    public void setProductsAsJSON(String productsAsJSON) {
        this.productsAsJSON = productsAsJSON;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", count=" + count +
                ", price=" + price +
                ", productsAsJSON='" + productsAsJSON + '\'' +
                '}';
    }
}
