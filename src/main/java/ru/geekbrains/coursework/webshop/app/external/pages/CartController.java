package ru.geekbrains.coursework.webshop.app.external.pages;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;
import ru.geekbrains.coursework.webshop.app.domain.ProductService;
import ru.geekbrains.coursework.webshop.app.domain.entities.Product;
import ru.geekbrains.coursework.webshop.app.external.pages.represantations.CartStatus;

import java.util.HashMap;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cart")
@SessionScope
public class CartController {
    private ProductService productService;
    private HashMap<Product, Integer> cart;
    private long fullPrice;
    private long productCount;

    @Autowired
    public CartController(ProductService productService) {
        this.cart = new HashMap<>();
        this.productService = productService;
    }

    @GetMapping
    public String show(Model model) {
        model.addAttribute("cart", this.cart.entrySet());
        model.addAttribute("relatedProducts", this.productService.getAll().parallelStream().limit(6).collect(Collectors.toList()));
        return "cart";
    }

    @PostMapping("/del/{id}")
    public String del(@PathVariable("id") long id) {
        this.cart.entrySet().removeIf(productIntegerEntry -> {
            if (productIntegerEntry.getKey().getId() == id) {

                this.productCount -= productIntegerEntry.getValue();
                this.fullPrice -= productIntegerEntry.getKey().getPrice() * productIntegerEntry.getValue();
                return true;
            }
            return false;
        });
        return "redirect:/cart";
    }

    @PostMapping("/add")
    public String add(@RequestParam("id") long id, @RequestParam(value = "count", defaultValue = "1") int count) {
        this.productService.getById(id)
                .ifPresent(product -> {
                    Hibernate.initialize(product.getImagesUrls());
                    cart.put(product, cart.getOrDefault(product, 0) + count);

                    this.fullPrice += product.getPrice() * count;
                    this.productCount += count;
                });
        return "redirect:/cart";
    }

    @GetMapping(value = "/count", produces = "application/json")
    public @ResponseBody
    CartStatus getStatus() {
        return this.createCartStatus();
    }

//    @GetMapping(value = "/add", produces = "application/json", consumes = "application/json")
//    public @ResponseBody CartStatus addToCart(@RequestParam("id") long id, @RequestParam(value = "count", defaultValue = "1") int count) {
//        this.add(id, count);
//        return this.createCartStatus();
//    }

//    @GetMapping(value = "/add", produces = "application/json", consumes = "application/json")
//    public @ResponseBody CartStatus addToCart(@RequestBody CartAddRequest cartAddRequest) {
//        this.add(cartAddRequest.getId(), cartAddRequest.getCount());
//        return this.createCartStatus();
//    }

    private CartStatus createCartStatus() {
        return new CartStatus(this.fullPrice, this.productCount);
    }

}
