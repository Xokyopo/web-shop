package ru.geekbrains.coursework.webshop.app.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import ru.geekbrains.coursework.webshop.app.CallCurrentMethodException;
import ru.geekbrains.coursework.webshop.app.dao.SaleRepository;
import ru.geekbrains.coursework.webshop.app.domain.entities.Brand;
import ru.geekbrains.coursework.webshop.app.domain.entities.Category;
import ru.geekbrains.coursework.webshop.app.domain.entities.Product;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SaleServiceTest {
    private final Random random = new Random();
    private SaleRepository saleRepository;
    private SaleService saleService;

    @BeforeEach
    public void init() {
        this.saleRepository = Mockito.mock(SaleRepository.class);
        this.saleService = new SaleService();
        this.saleService.init(this.saleRepository);
    }

    @Test
    public void getProductBySaleId_ShouldThrowException_WhenIdIsNull() {
        Class<IllegalArgumentException> expected = IllegalArgumentException.class;
        Executable actual = () -> this.saleService.getProductBySaleId(null);
        assertThrows(expected, actual);
    }

    @Test
    public void getProductBySaleId_ShouldThrowException_WhenEntityWithSetsIdNotFound() {
        Class<IllegalArgumentException> expected = IllegalArgumentException.class;
        Mockito.when(this.saleRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Executable actual = () -> this.saleService.getProductBySaleId(1L);
        assertThrows(expected, actual);
    }

    @Test
    public void sale_ShouldThrowException_WhenCartIsNull() {
        Class<IllegalArgumentException> expected = IllegalArgumentException.class;
        Executable actual = () -> this.saleService.sale(null);
        assertThrows(expected, actual);
    }

    @Test
    public void save_ShouldNotThrowException_WhenInputEmptyCart() {
        Exception exception = new CallCurrentMethodException("save_ShouldNotThrowException_WhenInputEmptyCart");
        Mockito.when(this.saleRepository.saveAll(Mockito.anyList())).thenThrow(exception);

        Executable actual = () -> this.saleService.sale(new HashMap<>());
        assertDoesNotThrow(actual);
    }

    @Test
    public void save_ShouldReturnTrue_WhenInputNotEmptyCart() {
        Exception exception = new CallCurrentMethodException("save_ShouldReturnTrue_WhenInputNotEmptyCart");
        Mockito.when(this.saleRepository.saveAll(Mockito.anyList())).thenThrow(exception);

        Class<CallCurrentMethodException> expected = CallCurrentMethodException.class;
        Executable actual = () -> this.saleService.sale(Map.of(this.createProduct(), 1));
        assertThrows(expected, actual);
    }

    private Product createProduct() {
        Product result = new Product();
        result.setId(1 + this.random.nextLong() * 100);
        result.setName("ProductName" + result.getId());
        result.setBrand(this.createBrand());
        result.setCategories(Set.of(this.createCategory()));
        result.setPrice(1000 + this.random.nextLong() * 100);
        result.setImagesUrls(Set.of("ProductImage"));
        return result;
    }

    private Brand createBrand() {
        Brand result = new Brand();
        result.setId(100 + this.random.nextLong() * 100);
        result.setName("brandName" + result.getId());
        result.setLogoUrl("BrandLogo");
        return result;
    }

    private Category createCategory() {
        Category result = new Category();
        result.setId(200 + this.random.nextLong() * 100);
        result.setName("categoryName" + result.getName());
        return result;
    }
}
