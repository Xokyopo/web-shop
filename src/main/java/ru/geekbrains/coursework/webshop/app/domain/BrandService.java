package ru.geekbrains.coursework.webshop.app.domain;

import org.springframework.stereotype.Service;
import ru.geekbrains.coursework.webshop.app.dao.BrandRepository;
import ru.geekbrains.coursework.webshop.app.domain.entities.Brand;

@Service
public class BrandService extends AService<Brand, BrandRepository> {
}
