package ru.geekbrains.coursework.webshop.app.external.pages.bootadmin.entities;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.coursework.webshop.app.domain.RoleService;
import ru.geekbrains.coursework.webshop.app.domain.entities.Role;

@Controller
@RequestMapping("/bootadmin/entities/role")
public class RoleController extends AController<Role, RoleService> {
}
