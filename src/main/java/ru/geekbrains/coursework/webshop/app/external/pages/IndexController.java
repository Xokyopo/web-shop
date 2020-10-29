package ru.geekbrains.coursework.webshop.app.external.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String getMainPage() {

        return "/index";
    }

    //TODO удалить
    @GetMapping("/cart")
    public String getCart() {
        return "/cart";
    }


    @GetMapping("/checkout")
    public String getCheckout() {
        return "/checkout";
    }

    @GetMapping("/shop")
    public String getShop() {
        return "/shop";
    }

    @GetMapping("/single-product")
    public String getSingleProduct() {
        return "/single-product";
    }
}
