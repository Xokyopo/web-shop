package ru.geekbrains.coursework.webshop.app.external.pages.bootadmin.entities;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.coursework.webshop.app.domain.BrandService;
import ru.geekbrains.coursework.webshop.app.domain.entities.Brand;

@Controller
@RequestMapping("/bootadmin/entities/brand")
public class BrandController extends AController<Brand, BrandService> {
}
