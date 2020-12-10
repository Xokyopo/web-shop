package ru.geekbrains.coursework.webshop.app.external.pages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.geekbrains.coursework.webshop.app.TestUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class SecurityControllerTest {

    private final TestUtils testUtils = new TestUtils();

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(new SecurityController())
                .setViewResolvers(testUtils.createThymeleafViewResolver()).build();
    }

    @Test
    public void getLoginPage_ShouldReturnLoginPage_WhenSendGetRequestToLoginEndpoint() throws Exception {
        this.mockMvc.perform(get("/login"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("login"));
    }

    @Test
    public void getLoginPage_ShouldReturnRegistrationPage_WhenSendGetRequestToRegistrationEndpoint() throws Exception {
        this.mockMvc.perform(get("/registration"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("registration"));
    }

}
