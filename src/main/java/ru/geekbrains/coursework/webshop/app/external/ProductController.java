package ru.geekbrains.coursework.webshop.app.external;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.coursework.webshop.app.domain.ProductService;
import ru.geekbrains.coursework.webshop.app.domain.entities.Product;

@Controller
@RequestMapping("product")
public class ProductController extends AController<Product, ProductService> {
}
