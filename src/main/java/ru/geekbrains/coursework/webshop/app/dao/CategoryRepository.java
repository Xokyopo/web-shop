package ru.geekbrains.coursework.webshop.app.dao;

import org.springframework.stereotype.Repository;
import ru.geekbrains.coursework.webshop.app.domain.entities.Category;

@Repository
public interface CategoryRepository extends ARepository<Category> {
}
