package ru.geekbrains.coursework.webshop.app.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.coursework.webshop.app.domain.entities.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/bootadmin")
public class BootAdminController {
    private Map<String, AController<?, ?>> entityControllers;

    @Autowired
    public void init(UserController userController,
                     ProductController productController,
                     BrandController brandController,
                     RoleController roleController,
                     CategoryController categoryController) {
        this.entityControllers = new HashMap<>();
        entityControllers.put("user", userController);
        entityControllers.put("product", productController);
        entityControllers.put("brand", brandController);
        entityControllers.put("role", roleController);
        entityControllers.put("category", categoryController);
    }

    @GetMapping
    public String showMainPage() {
        return "/bootadmin/summary";
    }

    @GetMapping("/entity/{name}/showAll")
    public String showAll(Model model, @PathVariable("name") String entityName) {
        this.entityControllers.get(entityName).showAll(model);
        return "/bootadmin/entities/" + entityName + "-list";
    }

    @GetMapping("/entity/{name}/show/{id}")
    public String showEntity(Model model, @PathVariable("name") String entityName, @PathVariable("id") Long id) {
        this.entityControllers.get(entityName).show(model, id);
        return "/bootadmin/entities/" + entityName + "-edit-form";
    }

    @GetMapping("/entity/{name}/del/{id}")
    public String deleteEntity(@PathVariable("name") String entityName, @PathVariable("id") Long id) {
        this.entityControllers.get(entityName).delete(id);
        return "redirect:/bootadmin/entity/" + entityName + "/showAll";
    }

    //TODO кастыли так как пока не придумал как это обойти
    @PostMapping("/entity/user/save")
    public String saveUser(User entity) {
        ((UserController) this.entityControllers.get("user")).save(entity);
        return "redirect:/bootadmin/entity/user/showAll";
    }

    @PostMapping("/entity/role/save")
    public String saveRole(Role entity) {
        ((RoleController) this.entityControllers.get("role")).save(entity);
        return "redirect:/bootadmin/entity/role/showAll";
    }

    @PostMapping("/entity/brand/save")
    public String saveBrand(Brand entity) {
        ((BrandController) this.entityControllers.get("brand")).save(entity);
        return "redirect:/bootadmin/entity/brand/showAll";
    }

    @PostMapping("/entity/category/save")
    public String saveCategory(Category entity) {
        ((CategoryController) this.entityControllers.get("category")).save(entity);
        return "redirect:/bootadmin/entity/category/showAll";
    }

    @PostMapping("/entity/product/save")
    public String saveProduct(Product entity) {
        ((ProductController) this.entityControllers.get("product")).save(entity);
        return "redirect:/bootadmin/entity/product/showAll";
    }

    @GetMapping("/testList")
    public String showTestTable(Model model) {
        //TODO not ready yet
        this.entityControllers.get("user").showAll(model);
        return "/bootadmin/entities/test-list";
    }

}
