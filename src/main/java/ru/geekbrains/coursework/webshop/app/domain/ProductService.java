package ru.geekbrains.coursework.webshop.app.domain;

import org.springframework.stereotype.Service;
import ru.geekbrains.coursework.webshop.app.dao.ProductRepository;
import ru.geekbrains.coursework.webshop.app.domain.entities.Product;

@Service
public class ProductService extends AService<Product, ProductRepository> {
    public ProductService() {
    }
}
