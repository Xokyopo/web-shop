package ru.geekbrains.coursework.webshop.app.external.pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;
import ru.geekbrains.coursework.webshop.app.domain.ProductService;
import ru.geekbrains.coursework.webshop.app.domain.SaleService;
import ru.geekbrains.coursework.webshop.app.domain.entities.Product;
import ru.geekbrains.coursework.webshop.app.external.pages.represantations.CartStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cart")
@SessionScope
public class CartController {
    private ProductService productService;
    private SaleService saleService;
    private HashMap<Product, Integer> cart;
    private long fullPrice;
    private long productCount;

    @Autowired
    public CartController(ProductService productService, SaleService saleService) {
        this.saleService = saleService;
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
                this.updateCartStatus(productIntegerEntry.getKey().getPrice(), -productIntegerEntry.getValue());
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
                    cart.put(product, cart.getOrDefault(product, 0) + count);
                    this.updateCartStatus(product.getPrice(), count);
                });
        return "redirect:/cart";
    }

    @PostMapping("/buy")
    public String buy(Model model) {
        this.saleService.sale(this.cart);
        Map<Product, Integer> buys = this.cart;
        this.cart = new HashMap<>();
        this.fullPrice = 0;
        this.productCount = 0;

        long chequeFullPrice = buys.entrySet().stream()
                .mapToLong(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();

        model.addAttribute("byes", buys.entrySet());
        model.addAttribute("fullPrice", chequeFullPrice);
        // redirect to buysList
        return "cheque";
    }

    @GetMapping(value = "/count")
    @ResponseBody
    public CartStatus getStatus() {
        return this.createCartStatus();
    }

    private void updateCartStatus(long price, long count) {
        this.productCount += count;
        this.fullPrice += price * count;
    }

    private CartStatus createCartStatus() {
        return new CartStatus(this.fullPrice, this.productCount);
    }

}
