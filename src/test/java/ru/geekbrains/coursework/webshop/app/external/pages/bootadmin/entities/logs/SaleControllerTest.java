package ru.geekbrains.coursework.webshop.app.external.pages.bootadmin.entities.logs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.geekbrains.coursework.webshop.app.TestUtils;
import ru.geekbrains.coursework.webshop.app.domain.SaleService;
import ru.geekbrains.coursework.webshop.app.domain.entities.Product;
import ru.geekbrains.coursework.webshop.app.domain.entities.Sale;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SaleControllerTest {
    private final TestUtils testUtils = new TestUtils();
    private SaleService saleService;
    private MockMvc mockMvc;


    @BeforeEach
    public void init() {
        this.saleService = Mockito.mock(SaleService.class);
        SaleController saleController = new SaleController(this.saleService);

        this.mockMvc = MockMvcBuilders
                .standaloneSetup(saleController)
                .setViewResolvers(this.testUtils.createThymeleafViewResolver())
                .build();
    }

    @Test
    public void showAll_ShouldReturnSaleListView_WhenSendGetToEndpoint() throws Exception {
        Mockito.when(this.saleService.getAll()).thenReturn(List.of(new Sale()));

        this.mockMvc.perform(get("/admin/entities/logs/sale/showAll"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("admin/entities/logs/sale-list"));
    }

    @Test
    public void showAll_ShouldExistSalesAttribute_WhenSendGetToEndpoint() throws Exception {
        Mockito.when(this.saleService.getAll()).thenReturn(List.of(new Sale()));

        this.mockMvc.perform(get("/admin/entities/logs/sale/showAll"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("sales"));
    }

    @Test
    public void showOne_ShouldReturnSaleProductInfoView_WhenSendGetToEndpointWithAnyId() throws Exception {
        Mockito.when(this.saleService.getProductBySaleId(Mockito.anyLong())).thenReturn(Optional.of(new Product()));

        this.mockMvc.perform(get("/admin/entities/logs/sale/show/{id}", 1))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("admin/entities/logs/sale-product-info"));
    }

    @Test
    public void showOne_ShouldExistProductAttribute_WhenSendGetToEndpointWithAnyId() throws Exception {
        Mockito.when(this.saleService.getProductBySaleId(Mockito.anyLong())).thenReturn(Optional.of(new Product()));

        this.mockMvc.perform(get("/admin/entities/logs/sale/show/{id}", 1))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("product"));
    }
}
