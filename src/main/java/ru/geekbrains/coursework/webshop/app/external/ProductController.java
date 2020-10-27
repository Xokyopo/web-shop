package ru.geekbrains.coursework.webshop.app.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.coursework.webshop.app.domain.BrandService;
import ru.geekbrains.coursework.webshop.app.domain.CategoryService;
import ru.geekbrains.coursework.webshop.app.domain.ProductService;
import ru.geekbrains.coursework.webshop.app.domain.entities.Product;

@Controller
@RequestMapping("product")
public class ProductController extends AController<Product, ProductService> {
    private CategoryService categoryService;
    private BrandService brandService;

    @Autowired
    public ProductController(CategoryService categoryService, BrandService brandService) {
        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    @Override
    @GetMapping({"/show/{id}"})
    public String show(Model model, Long id) {
        model.addAttribute("allBrands", brandService.getAll());
        model.addAttribute("allCategories", categoryService.getAll());
        return super.show(model, id);
    }
}
