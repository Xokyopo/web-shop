package ru.geekbrains.coursework.webshop.app.external.pages.bootadmin.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.geekbrains.coursework.webshop.app.TestUtils;
import ru.geekbrains.coursework.webshop.app.domain.RoleService;
import ru.geekbrains.coursework.webshop.app.domain.UserService;
import ru.geekbrains.coursework.webshop.app.domain.entities.Role;
import ru.geekbrains.coursework.webshop.app.domain.entities.User;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {
    private final TestUtils testUtils = new TestUtils();
    private UserService userService;
    private RoleService roleService;
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        this.roleService = Mockito.mock(RoleService.class);
        this.userService = Mockito.mock(UserService.class);
        Mockito.when(this.userService.getEntityName()).thenReturn(User.class.getSimpleName());

        UserController userController = new UserController(this.roleService);
        userController.init(this.userService);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .setViewResolvers(this.testUtils.createThymeleafViewResolver())
                .build();
    }

    @Test
    public void show_ShouldExistAllRolesAttribute_WhenSendGetToEndpoint() throws Exception {
        Mockito.when(this.userService.getById(Mockito.anyLong())).thenReturn(Optional.of(new User()));
        Mockito.when(this.roleService.getAll()).thenReturn(List.of(new Role()));

        this.mockMvc.perform(get("/admin/entities/user/show/{id}", 1))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("allRoles"));
    }
}
