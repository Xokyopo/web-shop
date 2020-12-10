package ru.geekbrains.coursework.webshop.app.external.pages.bootadmin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.geekbrains.coursework.webshop.app.TestUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class BootAdminMainControllerTest {
    private TestUtils testUtils = new TestUtils();
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(BootAdminMainController.class)
                .setViewResolvers(this.testUtils.createThymeleafViewResolver())
                .build();
    }

    @Test
    public void showMainPage_ShouldReturnGreetingView_WhenSendGetToEndpoint() throws Exception {
        this.mockMvc.perform(get("/admin"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("admin/greeting"));
    }
}
