package ru.geekbrains.coursework.webshop.app;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import java.util.List;

public class TestUtils {

    public ThymeleafViewResolver createThymeleafViewResolver() {
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        thymeleafViewResolver.setTemplateEngine(new SpringTemplateEngine());
        return thymeleafViewResolver;
    }

    public <T> Page<T> getPageOf(T... elements) {
        return new PageImpl<>(List.of(elements));
    }
}
