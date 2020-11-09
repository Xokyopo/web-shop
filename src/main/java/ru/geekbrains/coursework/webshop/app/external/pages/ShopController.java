package ru.geekbrains.coursework.webshop.app.external.pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.coursework.webshop.app.domain.ProductService;

import java.util.Optional;

@Controller
@RequestMapping("/shop")
public class ShopController {
    private static final int PRODUCT_LIMIT_ON_PAGE = 12; //default 12
    private ProductService productService;
    private CartController cartController;

    @Autowired
    public ShopController(ProductService productService, CartController cartController) {
        this.productService = productService;
        this.cartController = cartController;
    }

    @GetMapping
    public String show(Model model, @RequestParam("page") Optional<Integer> page) {
        this.cartController.setCartStat(model);
        model.addAttribute("productsPages", this.productService.getAll(PageRequest.of(page.orElse(1) - 1, PRODUCT_LIMIT_ON_PAGE)));
        return "shop";
    }

    @PostMapping("/addToCart")
    public String add(@RequestParam("id") long id) {
        this.cartController.add(id, 1);
        return "redirect:/shop";
    }
}
