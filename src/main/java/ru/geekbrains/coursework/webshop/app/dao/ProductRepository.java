package ru.geekbrains.coursework.webshop.app.dao;

import org.springframework.stereotype.Repository;
import ru.geekbrains.coursework.webshop.app.domain.entities.Product;

@Repository
public interface ProductRepository extends ARepository<Product> {
}
