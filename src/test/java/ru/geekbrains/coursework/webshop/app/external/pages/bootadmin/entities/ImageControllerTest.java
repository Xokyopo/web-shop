package ru.geekbrains.coursework.webshop.app.external.pages.bootadmin.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MimeTypeUtils;
import ru.geekbrains.coursework.webshop.app.TestUtils;
import ru.geekbrains.coursework.webshop.app.domain.ImageService;
import ru.geekbrains.coursework.webshop.app.domain.entities.Image;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageControllerTest {
    private final TestUtils testUtils = new TestUtils();
    private ImageService imageService;
    private ImageController imageController;
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        this.imageService = Mockito.mock(ImageService.class);
        Mockito.when(this.imageService.getEntityName()).thenReturn(Image.class.getSimpleName());
        this.imageController = new ImageController();
        this.imageController.init(this.imageService);

        this.mockMvc = MockMvcBuilders
                .standaloneSetup(this.imageController)
                .setViewResolvers(this.testUtils.createThymeleafViewResolver())
                .build();
    }

    @Test
    public void showAll_ShouldExistProgramUtilsAttribute_WhenSendGetRequestToEndpoint() throws Exception {
        Mockito.when(this.imageService.getAll()).thenReturn(List.of(new Image()));
        this.mockMvc.perform(get("/admin/entities/image/showAll"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("programUtils"));
    }

    @Test
    public void show_ShouldExistProgramUtilsAttribute_WhenSendGetRequestToEndpointWithAnyId() throws Exception {
        Mockito.when(this.imageService.getById(Mockito.anyLong())).thenReturn(Optional.of(new Image()));
        this.mockMvc.perform(get("/admin/entities/image/show/{Id}", 1))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("programUtils"));
    }

    @Test
    public void save_ShouldExistProgramUtilsAttribute_WhenSendPostRequestToEndpointWithAnyId() throws Exception {
        this.mockMvc.perform(multipart("/admin/entities/image/save")
                .file("uploadList", new byte[1]))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/entities/image/showAll"));
    }

    @Test
    public void getAsResource_ShouldContainExpectedData_WhenSendGetRequestToEndpointWhitExistingId() throws Exception {
        Image expected = new Image();
        expected.setData("1234567890".getBytes(StandardCharsets.UTF_8));
        expected.setType(MimeTypeUtils.IMAGE_JPEG_VALUE);

        Mockito.when(this.imageService.getById(Mockito.anyLong())).thenReturn(Optional.of(expected));

        this.mockMvc.perform(get("/admin/entities/image/get/{id}", 1))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.valueOf(expected.getType())))
                .andExpect(content().bytes(expected.getData()));
    }

    @Test
    public void getAsResource_ShouldReturnError_WhenSendGetRequestToEndpointWhitNotExistingId() throws Exception {
        this.mockMvc.perform(get("/admin/entities/image/get/{id}", 1))
                .andExpect(status().is4xxClientError());
    }
}
