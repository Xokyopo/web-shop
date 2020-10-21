package ru.geekbrains.coursework.webshop.app.domain;

import org.springframework.stereotype.Service;
import ru.geekbrains.coursework.webshop.app.dao.UserRepository;
import ru.geekbrains.coursework.webshop.app.domain.entities.User;

@Service
public class UserService extends AService<User, UserRepository> {
    public UserService() {
    }
}
