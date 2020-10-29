package ru.geekbrains.coursework.webshop.app.external.pages.bootadmin.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.coursework.webshop.app.domain.RoleService;
import ru.geekbrains.coursework.webshop.app.domain.UserService;
import ru.geekbrains.coursework.webshop.app.domain.entities.User;

@Controller
@RequestMapping("/bootadmin/entities/user")
public class UserController extends AController<User, UserService> {
    private RoleService roleService;

    @Autowired
    public UserController(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    @GetMapping("/show/{id}")
    public String show(Model model, @PathVariable("id") Long id) {
        model.addAttribute("allRoles", roleService.getAll());
        return super.show(model, id);
    }
}
