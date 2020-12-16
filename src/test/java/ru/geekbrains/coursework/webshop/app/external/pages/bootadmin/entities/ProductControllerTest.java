package ru.geekbrains.coursework.webshop.app.external.pages.bootadmin.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.geekbrains.coursework.webshop.app.TestUtils;
import ru.geekbrains.coursework.webshop.app.domain.BrandService;
import ru.geekbrains.coursework.webshop.app.domain.CategoryService;
import ru.geekbrains.coursework.webshop.app.domain.ProductService;
import ru.geekbrains.coursework.webshop.app.domain.entities.Brand;
import ru.geekbrains.coursework.webshop.app.domain.entities.Category;
import ru.geekbrains.coursework.webshop.app.domain.entities.Product;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductControllerTest {
    private final TestUtils testUtils = new TestUtils();
    private CategoryService categoryService;
    private BrandService brandService;
    private ProductService productService;
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        this.categoryService = Mockito.mock(CategoryService.class);
        this.brandService = Mockito.mock(BrandService.class);
        this.productService = Mockito.mock(ProductService.class);
        ProductController productController = new ProductController(this.categoryService, this.brandService);
        Mockito.when(this.productService.getEntityName()).thenReturn(Product.class.getSimpleName());
        productController.init(this.productService);

        this.mockMvc = MockMvcBuilders
                .standaloneSetup(productController)
                .setViewResolvers(this.testUtils.createThymeleafViewResolver())
                .build();
    }

    @Test
    public void show_ShouldExistAllBrandsAttribute_WhenSendGetRequestToEndpointWithAnyId() throws Exception {
        Mockito.when(this.brandService.getAll()).thenReturn(List.of(new Brand()));
        Mockito.when(this.categoryService.getAll()).thenReturn(List.of(new Category()));
        Mockito.when(this.productService.getById(Mockito.anyLong())).thenReturn(Optional.of(new Product()));

        this.mockMvc.perform(get("/admin/entities/product/show/{id}", 1))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("allBrands"));
    }

    @Test
    public void show_ShouldExistAllCategoriesAttribute_WhenSendGetRequestToEndpointWithAnyId() throws Exception {
        Mockito.when(this.brandService.getAll()).thenReturn(List.of(new Brand()));
        Mockito.when(this.categoryService.getAll()).thenReturn(List.of(new Category()));
        Mockito.when(this.productService.getById(Mockito.anyLong())).thenReturn(Optional.of(new Product()));

        this.mockMvc.perform(get("/admin/entities/product/show/{id}", 1))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("allCategories"));
    }
}
