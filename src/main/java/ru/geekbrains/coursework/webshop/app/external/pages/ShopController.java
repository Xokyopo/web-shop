package ru.geekbrains.coursework.webshop.app.external.pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.coursework.webshop.app.domain.ProductService;

import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ShopController {
    private static final int PRODUCT_LIMIT_ON_PAGE = 12; //default 12
    private final ProductService productService;
    private final CartController cartController;

    @Autowired
    public ShopController(ProductService productService, CartController cartController) {
        this.productService = productService;
        this.cartController = cartController;
    }

    @GetMapping
    public String getMainPage() {
        return "index";
    }

    @GetMapping("/shop")
    public String show(Model model, @RequestParam("page") Optional<Integer> page) {
        model.addAttribute("productsPages", this.productService.getPage(PageRequest.of(page.orElse(1) - 1, PRODUCT_LIMIT_ON_PAGE)));
        return "shop";
    }

    @PostMapping("/shop/addToCart")
    public String add(@RequestParam("id") long id) {
        this.cartController.add(id, 1);
        return "redirect:/shop";
    }

    @GetMapping("/product/{id}")
    public String showSingleProduct(Model model, @PathVariable("id") long id) {
        if (id == 0) throw new IllegalArgumentException(
                "id in {public String showSingleProduct(Model model, @PathVariable(\"id\") long id)} can be 0");
        model.addAttribute("product", this.productService.getById(id).orElseThrow(IllegalArgumentException::new));
        model.addAttribute("relatedProducts", this.productService.getAll().parallelStream().limit(6).collect(Collectors.toList()));

        return "single-product";
    }

    @PostMapping("/product/addToCart")
    public String add(@RequestParam("id") long id, @RequestParam(value = "count", defaultValue = "1") int count) {
        this.cartController.add(id, count);
        return "redirect:/product/" + id;
    }

//    // test method who return shop fragment [it's work ^) ahahahaah]
//    @GetMapping("/frag")
//    public String showFrag(Model model, @RequestParam("page") Optional<Integer> page) {
//        model.addAttribute("productsPages", this.productService.getPage(PageRequest.of(page.orElse(1) - 1, PRODUCT_LIMIT_ON_PAGE)));
//        return "shop :: test";
//    }
}
