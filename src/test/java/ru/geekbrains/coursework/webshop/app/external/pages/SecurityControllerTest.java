package ru.geekbrains.coursework.webshop.app.external.pages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class SecurityControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        thymeleafViewResolver.setTemplateEngine(new SpringTemplateEngine());

        mockMvc = MockMvcBuilders.standaloneSetup(new SecurityController())
                .setViewResolvers(thymeleafViewResolver).build();
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
