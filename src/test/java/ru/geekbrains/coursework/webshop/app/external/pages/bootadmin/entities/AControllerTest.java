package ru.geekbrains.coursework.webshop.app.external.pages.bootadmin.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.coursework.webshop.app.TestUtils;
import ru.geekbrains.coursework.webshop.app.dao.ARepository;
import ru.geekbrains.coursework.webshop.app.domain.AService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AControllerTest {
    private final TestUtils testUtils = new TestUtils();
    private TestService testService;
    private TestController testController;
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        this.testService = Mockito.mock(TestService.class);
        Mockito.when(this.testService.getEntityName()).thenReturn(TestEntity.class.getSimpleName());
        this.testController = new TestController();
        this.testController.init(this.testService);

        this.mockMvc = MockMvcBuilders
                .standaloneSetup(this.testController)
                .setViewResolvers(this.testUtils.createThymeleafViewResolver())
                .build();
    }

    @Test
    public void getService_ShouldReturnCurrentTestService_WhenExecuteMethod() {
        TestService expected = this.testService;
        TestService actual = this.testController.getService();

        assertEquals(expected, actual);
    }

    @Test
    public void showAll_ShouldReturnTestEntityView_WhenSendGetToEndpoint() throws Exception {
        this.mockMvc.perform(get("/test/showAll"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("test-list"));
    }

    @Test
    public void showAll_ShouldReturnTestEntityAttribute_WhenSendGetToEndpoint() throws Exception {
        this.mockMvc.perform(get("/test/showAll"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists(TestEntity.class.getSimpleName().toLowerCase() + "s"));
    }

    @Test
    public void show_ShouldReturnEditFormView_WhenSendGetToEndpoint() throws Exception {
        Mockito.when(this.testService.getById(Mockito.anyLong())).thenReturn(Optional.of(new TestEntity()));
        this.mockMvc.perform(get("/test/show/{id}", Mockito.anyLong()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("test-edit-form"));
    }

    @Test
    public void show_ShouldReturnTestEntityAttribute_WhenSendGetToEndpoint() throws Exception {
        Mockito.when(this.testService.getById(Mockito.anyLong())).thenReturn(Optional.of(new TestEntity()));
        this.mockMvc.perform(get("/test/show/{id}", Mockito.anyLong()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists(TestEntity.class.getSimpleName().toLowerCase()));
    }

    @Test
    public void delete_ShouldReturnRedirectToShowAll_WhenSendGetToEndpoint() throws Exception {
        this.mockMvc.perform(post("/test/del/{id}", Mockito.anyLong()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/test/showAll"));
    }

    @Test
    public void save_ShouldReturnRedirectToShowAll_WhenSendGetToEndpoint() throws Exception {

        this.mockMvc.perform(post("/test/save")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", Long.toString(1))
                .param("name", "name1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/test/showAll"));
    }

    private interface TestRepository extends ARepository<TestEntity> {
    }

    private static class TestEntity {
        private long id;
        private String name;

        public TestEntity() {
        }

        public TestEntity(long id, String name) {
            this.id = id;
            this.name = name;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private static class TestService extends AService<TestEntity, TestRepository> {
    }

    @Controller
    @RequestMapping("test")
    private static class TestController extends AController<TestEntity, TestService> {
    }
}
