package ru.geekbrains.coursework.webshop.app;

import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

public class TestUtils {

    public ThymeleafViewResolver createThymeleafViewResolver() {
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        thymeleafViewResolver.setTemplateEngine(new SpringTemplateEngine());
        return thymeleafViewResolver;
    }
}
