package ru.geekbrains.coursework.webshop.app.external.pages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.geekbrains.coursework.webshop.app.TestUtils;
import ru.geekbrains.coursework.webshop.app.domain.ProductService;
import ru.geekbrains.coursework.webshop.app.domain.entities.Product;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ShopControllerTest {
    private final TestUtils testUtils = new TestUtils();
    private ProductService productService;
    private CartController cartController;
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        this.productService = Mockito.mock(ProductService.class);
        this.cartController = Mockito.mock(CartController.class);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new ShopController(this.productService, this.cartController))
                .setViewResolvers(this.testUtils.createThymeleafViewResolver())
                .build();
    }

    @Test
    public void getMainPage_ShouldReturnMainPage_WhenSendGetRequestToStartEndpoint() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("index"));
    }

    @Test
    public void showShop_ShouldReturnShopPage_WhenSendGetRequestToShopEndpoint() throws Exception {
        this.mockMvc.perform(get("/shop"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("shop"));
    }

    @Test
    public void showShop_ShouldExistProductsPagesAttribute_WhenSendGetRequestToShopEndpoint() throws Exception {
        Mockito.when(this.productService.getPage(Mockito.any())).thenReturn(this.testUtils.getPageOf(new Product()));
        this.mockMvc.perform(get("/shop"))
                .andExpect(model().attributeExists("productsPages"));
    }

    @Test
    public void addToCartForShopPage_ShouldReturnRedirectToShopPage_WhenSendGetRequestToShopAddCartEndpoint() throws Exception {
        Mockito.when(this.cartController.add(Mockito.anyLong(), Mockito.anyInt())).thenReturn("");
        this.mockMvc.perform(
                post("/shop/addToCart")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/shop"));
    }

    @Test
    public void addToCartForProductPage_ShouldReturnRedirectToProductPage_WhenSendGetRequestToProductAddCartEndpoint() throws Exception {
        long id = 1;
        Mockito.when(this.cartController.add(Mockito.anyLong(), Mockito.anyInt())).thenReturn("");
        this.mockMvc.perform(
                post("/product/addToCart")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("id", Long.toString(id))
                        .param("count", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/" + id));
    }

    @Test
    public void showSingleProduct_ShouldReturnProductPage_WhenSendGetRequestToProductEndpoint() throws Exception {
        long id = 1;
        Mockito.when(this.productService.getById(Mockito.anyLong())).thenReturn(Optional.of(new Product()));
        Mockito.when(this.productService.getAll()).thenReturn(List.of(new Product()));
        this.mockMvc.perform(get("/product/{id}", id))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("single-product"));
    }

    @Test
    public void showSingleProduct_ShouldExistProductAttribute_WhenSendGetRequestToProductEndpoint() throws Exception {
        Mockito.when(this.productService.getById(Mockito.anyLong())).thenReturn(Optional.of(new Product()));
        Mockito.when(this.productService.getAll()).thenReturn(List.of(new Product()));
        this.mockMvc.perform(get("/product/{id}", 1))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("product"));
    }

    @Test
    public void showSingleProduct_ShouldExistRelatedProductsAttribute_WhenSendGetRequestToProductEndpoint() throws Exception {
        Mockito.when(this.productService.getById(Mockito.anyLong())).thenReturn(Optional.of(new Product()));
        Mockito.when(this.productService.getAll()).thenReturn(List.of(new Product()));
        this.mockMvc.perform(get("/product/{id}", 1))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("relatedProducts"));
    }
}
