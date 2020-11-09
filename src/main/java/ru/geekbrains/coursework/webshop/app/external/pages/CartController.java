package ru.geekbrains.coursework.webshop.app.external.pages;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;
import ru.geekbrains.coursework.webshop.app.domain.ProductService;
import ru.geekbrains.coursework.webshop.app.domain.entities.Product;

import java.util.HashMap;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cart")
@SessionScope
public class CartController {
    private ProductService productService;
    private HashMap<Product, Integer> cart;

    @Autowired
    public CartController(ProductService productService) {
        this.cart = new HashMap<>();
        this.productService = productService;
    }

    @GetMapping
    public String show(Model model) {
        model.addAttribute("cart", this.cart.entrySet());
        this.setCartStat(model);
        model.addAttribute("relatedProducts", this.productService.getAll().parallelStream().limit(6).collect(Collectors.toList()));
        return "cart";
    }

    @PostMapping("/del/{id}")
    public String del(@PathVariable("id") long id) {
        this.cart.entrySet().removeIf(productIntegerEntry -> productIntegerEntry.getKey().getId() == id);
        return "redirect:/cart";
    }

    @PostMapping("/add")
    public String add(@RequestParam("id") long id, @RequestParam(value = "count", defaultValue = "1") int count) {
        this.productService.getById(id)
                .ifPresent(product -> {
                    Hibernate.initialize(product.getImagesUrls());
                    cart.put(product, cart.getOrDefault(product, 0) + count);
                });
        return "redirect:/cart";
    }

    private long getFullPrice() {
        return cart.entrySet().stream()
                .mapToLong(value -> value.getKey().getPrice() * value.getValue())
                .sum();
    }

    private long getProductCount() {
        return cart.values().stream()
                .mapToLong(Integer::longValue)
                .sum();
    }

    public void setCartStat(Model model) {
        if (!this.cart.isEmpty()) {
            model.addAttribute("fullPrice", this.getFullPrice());
            model.addAttribute("productCount", this.getProductCount());
        }
    }
}
