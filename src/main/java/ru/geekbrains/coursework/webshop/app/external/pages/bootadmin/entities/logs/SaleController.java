package ru.geekbrains.coursework.webshop.app.external.pages.bootadmin.entities.logs;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.coursework.webshop.app.domain.SaleService;
import ru.geekbrains.coursework.webshop.app.external.exceptions.EntityNotFoundException;

@Controller
@RequestMapping("/admin/entities/logs/sale")
public class SaleController {
    private SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping("/showAll")
    public String showAll(Model model) {
        model.addAttribute("sales", saleService.getAll());
        return "admin/entities/logs/sale-list";
    }

    @GetMapping("/show/{id}")
    public String showOne(Model model, @PathVariable("id") long id) {
        model.addAttribute("product", saleService.getProductBySaleId(id).orElseThrow(EntityNotFoundException::new));
        return "admin/entities/logs/sale-product-info";
    }
}
