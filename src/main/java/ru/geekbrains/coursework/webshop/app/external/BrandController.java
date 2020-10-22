package ru.geekbrains.coursework.webshop.app.external;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.coursework.webshop.app.domain.BrandService;
import ru.geekbrains.coursework.webshop.app.domain.entities.Brand;

@Controller
@RequestMapping("/brand")
public class BrandController extends AController<Brand, BrandService> {
}
