package ru.geekbrains.coursework.webshop.app.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ru.geekbrains.coursework.webshop.app.dao.SaleRepository;
import ru.geekbrains.coursework.webshop.app.domain.entities.Product;
import ru.geekbrains.coursework.webshop.app.domain.entities.Sale;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SaleService extends AService<Sale, SaleRepository> {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    public List<Sale> sale(Map<Product, Integer> cart) {
        List<Sale> sales = cart.entrySet().stream().map(this::createSale).collect(Collectors.toList());
        this.getRepository().saveAll(sales);
        // need return human readable list
        return sales;
    }

    private Sale createSale(Map.Entry<Product, Integer> cartItem) {
        //lazy init exception not resolved
        return new Sale(
                System.currentTimeMillis(),
                cartItem.getValue(),
                cartItem.getKey().getPrice(),
                this.convertProductToJson(cartItem.getKey())
        );
    }

    public Optional<Product> getProductBySaleId(Long id) {
        return convertJsonToProduct(
                this.getById(id).orElseThrow(() -> new IllegalArgumentException("Not have sale with id [" + id + "]")
                ).getProductsAsJSON());
    }

    private Optional<Product> convertJsonToProduct(String json) {
        try {
            Product result = getObjectMapper().readValue(json, Product.class);
            return Optional.of(result);
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }

    private String convertProductToJson(Product product) {
        try {
            return getObjectMapper().writeValueAsString(product);
        } catch (JsonProcessingException e) {
            return "error covert entity to JSON e {" + e.getMessage() + "}";
        }
    }
}
