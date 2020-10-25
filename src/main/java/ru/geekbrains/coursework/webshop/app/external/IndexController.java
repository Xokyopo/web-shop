package ru.geekbrains.coursework.webshop.app.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.coursework.webshop.app.domain.AllServiceAccumulator;

@Controller
@RequestMapping("/")
public class IndexController {
    private AllServiceAccumulator allServiceAccumulator;

    @Autowired
    public IndexController(AllServiceAccumulator allServiceAccumulator) {
        this.allServiceAccumulator = allServiceAccumulator;
    }

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
//single-product
    public String getSingleProduct() {
        return "/single-product";
    }
}
