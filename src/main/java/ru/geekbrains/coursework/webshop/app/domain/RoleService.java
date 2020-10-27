package ru.geekbrains.coursework.webshop.app.domain;

import org.springframework.stereotype.Service;
import ru.geekbrains.coursework.webshop.app.dao.RoleRepository;
import ru.geekbrains.coursework.webshop.app.domain.entities.Role;

@Service
public class RoleService extends AService<Role, RoleRepository> {
}
