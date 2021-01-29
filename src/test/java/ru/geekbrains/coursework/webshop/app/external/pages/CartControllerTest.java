package ru.geekbrains.coursework.webshop.app.external.pages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.geekbrains.coursework.webshop.app.TestUtils;
import ru.geekbrains.coursework.webshop.app.domain.ProductService;
import ru.geekbrains.coursework.webshop.app.domain.SaleService;
import ru.geekbrains.coursework.webshop.app.domain.entities.Product;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CartControllerTest {
    private final TestUtils testUtils = new TestUtils();
    private MockMvc mockMvc;
    private ProductService productService;
    private SaleService saleService;
    private CartController cartController;

    @BeforeEach
    public void init() {
        this.productService = Mockito.mock(ProductService.class);
        this.saleService = Mockito.mock(SaleService.class);
        this.cartController = new CartController(this.productService, this.saleService);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(this.cartController)
                .setViewResolvers(this.testUtils.createThymeleafViewResolver())
                .build();
    }

    @Test
    public void show_ShouldReturnCartView_WhenSendGetToEndpoint() throws Exception {
        Mockito.when(this.productService.getAll()).thenReturn(List.of(new Product()));

        this.mockMvc.perform(get("/cart"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("cart"));
    }

    @Test
    public void show_ShouldReturnCartAttribute_WhenSendGetToEndpoint() throws Exception {
        Mockito.when(this.productService.getAll()).thenReturn(List.of(new Product()));

        this.mockMvc.perform(get("/cart"))
                .andExpect(model().attributeExists("cart"));
    }

    @Test
    public void show_ShouldReturnRelatedProductsAttribute_WhenSendGetToEndpoint() throws Exception {
        Mockito.when(this.productService.getAll()).thenReturn(List.of(new Product()));

        this.mockMvc.perform(get("/cart"))
                .andExpect(model().attributeExists("relatedProducts"));
    }

    @Test
    public void del_ShouldReturnRedirectToCart_WhenSendPostRequestToEndpoint() throws Exception {
        this.mockMvc.perform(post("/cart/del/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart"));
    }

    @Test
    public void del_ShouldReturnOne_WhenDeleteOneProductById() {
        Product productFirst = new Product();
        productFirst.setId(1);
        Product productSecond = new Product();
        productSecond.setId(2);
        Mockito.when(this.productService.getById(Mockito.eq(1L))).thenReturn(Optional.of(productFirst));
        Mockito.when(this.productService.getById(Mockito.eq(2L))).thenReturn(Optional.of(productSecond));

        this.cartController.add(1, 1);
        this.cartController.add(2, 1);

        this.cartController.del(1);

        long expected = 1;
        long actual = this.cartController.getStatus().getProductCount();
        assertEquals(expected, actual);
    }

    @Test
    public void del_ShouldGetReducingTheTotalCost_WhenDeleteOneProduct() {
        Product productFirst = new Product();
        productFirst.setId(1);
        productFirst.setPrice(10);
        Product productSecond = new Product();
        productSecond.setId(2);
        productSecond.setPrice(20);
        Mockito.when(this.productService.getById(Mockito.eq(1L))).thenReturn(Optional.of(productFirst));
        Mockito.when(this.productService.getById(Mockito.eq(2L))).thenReturn(Optional.of(productSecond));

        this.cartController.add(1, 1);
        this.cartController.add(2, 1);

        this.cartController.del(1);

        long expected = 30;
        long actual = this.cartController.getStatus().getFullPrice();
        assertTrue(expected > actual);
    }

    @Test
    public void add_ShouldReturnRedirectToCart_WhenSendPostToEndpoint() throws Exception {
        Product product = new Product();
        product.setPrice(10);
        Mockito.when(this.productService.getById(Mockito.anyLong())).thenReturn(Optional.of(product));

        this.mockMvc.perform(
                post("/cart/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", Long.toString(1)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart"));
    }

    @Test
    public void add_ShouldAddOneProductToCart_WhenSendPostToEndpointWithProductIdOnly() throws Exception {
        Product product = new Product();
        product.setPrice(10);
        Mockito.when(this.productService.getById(Mockito.anyLong())).thenReturn(Optional.of(product));

        this.mockMvc.perform(
                post("/cart/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", Long.toString(1)))
                .andExpect(status().is3xxRedirection());

        long expected = 1;
        long actual = this.cartController.getStatus().getProductCount();
        assertEquals(expected, actual);
    }

    @Test
    public void add_ShouldReturnTwo_WhenSetTwoAsSecondParameter() {
        Product product = new Product();
        product.setPrice(10);
        Mockito.when(this.productService.getById(Mockito.anyLong())).thenReturn(Optional.of(product));
        this.cartController.add(1, 2);

        long expected = 2;
        long actual = this.cartController.getStatus().getProductCount();
        assertEquals(expected, actual);
    }

    @Test
    public void add_ShouldReturnTrue_WhenCompareCalculatedAndReturnedFullPrice() throws Exception {
        Product product = new Product();
        product.setPrice(10);
        Mockito.when(this.productService.getById(Mockito.anyLong())).thenReturn(Optional.of(product));
        this.cartController.add(1, 2);

        long expected = 20;
        long actual = this.cartController.getStatus().getFullPrice();

        assertEquals(expected, actual);
    }

    @Test
    public void buy_ShouldReturnChequeView_WhenSendPostToEndpoint() throws Exception {
        Mockito.doNothing().when(this.saleService).sale(Mockito.anyMap());

        this.mockMvc.perform(post("/cart/buy"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("cheque"));
    }

    @Test
    public void buy_ShouldExistByesAttribute_WhenSendPostToEndpoint() throws Exception {
        Mockito.doNothing().when(this.saleService).sale(Mockito.anyMap());

        this.mockMvc.perform(post("/cart/buy"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("byes"));
    }

    @Test
    public void buy_ShouldExistFullPriceAttribute_WhenSendPostToEndpoint() throws Exception {
        Mockito.doNothing().when(this.saleService).sale(Mockito.anyMap());

        this.mockMvc.perform(post("/cart/buy"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("fullPrice"));
    }

    @Test
    public void buy_ShouldGetZeroInFullPrice_WhenAddProductAndSendPostToEndpoint() throws Exception {
        Product product = new Product();
        product.setPrice(10);
        Mockito.when(this.productService.getById(Mockito.anyLong())).thenReturn(Optional.of(product));
        this.cartController.add(1, 2);
        Mockito.doNothing().when(this.saleService).sale(Mockito.anyMap());

        this.mockMvc.perform(post("/cart/buy"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("fullPrice"));

        long expected = 0;
        long actual = this.cartController.getStatus().getFullPrice();

        assertEquals(expected, actual);
    }

    @Test
    public void buy_ShouldGetZeroInProductCount_WhenSendPostToEndpoint() throws Exception {
        Product product = new Product();
        product.setPrice(10);
        Mockito.when(this.productService.getById(Mockito.anyLong())).thenReturn(Optional.of(product));
        this.cartController.add(1, 2);

        Mockito.doNothing().when(this.saleService).sale(Mockito.anyMap());

        this.mockMvc.perform(post("/cart/buy"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("fullPrice"));

        long expected = 0;
        long actual = this.cartController.getStatus().getProductCount();

        assertEquals(expected, actual);
    }

    @Test
    public void getStatus_ShouldReturnCartStatus_WhenSendGetToEndpoint() throws Exception {
        Product product = new Product();
        product.setPrice(10);

        Mockito.when(this.productService.getById(Mockito.anyLong())).thenReturn(Optional.of(product));
        this.cartController.add(1, 2);

        this.mockMvc.perform(get("/cart/count"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fullPrice", is(20)))
                .andExpect(jsonPath("$.productCount", is(2)));
    }
}
