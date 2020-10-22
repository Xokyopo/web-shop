package ru.geekbrains.coursework.webshop.app.external;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.coursework.webshop.app.domain.UserService;
import ru.geekbrains.coursework.webshop.app.domain.entities.User;

@Controller
@RequestMapping("user")
public class UserController extends AController<User, UserService> {
}
