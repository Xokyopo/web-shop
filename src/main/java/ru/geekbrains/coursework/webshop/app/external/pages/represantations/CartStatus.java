package ru.geekbrains.coursework.webshop.app.external.pages.represantations;

public class CartStatus {
    private long fullPrice;
    private long productCount;

    public CartStatus() {
    }

    public CartStatus(long fullPrice, long productCount) {
        this.fullPrice = fullPrice;
        this.productCount = productCount;
    }

    public long getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(long fullPrice) {
        this.fullPrice = fullPrice;
    }

    public long getProductCount() {
        return productCount;
    }

    public void setProductCount(long productCount) {
        this.productCount = productCount;
    }
}
