package ru.geekbrains.coursework.webshop.app.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ru.geekbrains.coursework.webshop.app.dao.SaleRepository;
import ru.geekbrains.coursework.webshop.app.domain.entities.Product;
import ru.geekbrains.coursework.webshop.app.domain.entities.Sale;
import ru.geekbrains.coursework.webshop.app.utils.ProgramUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SaleService extends AService<Sale, SaleRepository> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
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
                ProgramUtils.exceptionReplacer(() -> objectMapper.writeValueAsString(cartItem.getKey()), new IllegalArgumentException())
        );
    }
}
