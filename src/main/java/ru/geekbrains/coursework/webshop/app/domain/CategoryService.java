package ru.geekbrains.coursework.webshop.app.domain;

import org.springframework.stereotype.Service;
import ru.geekbrains.coursework.webshop.app.dao.CategoryRepository;
import ru.geekbrains.coursework.webshop.app.domain.entities.Category;

@Service
public class CategoryService extends AService<Category, CategoryRepository> {
    public CategoryService() {
    }
}
