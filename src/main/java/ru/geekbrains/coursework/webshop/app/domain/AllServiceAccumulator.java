package ru.geekbrains.coursework.webshop.app.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AllServiceAccumulator {
    private BrandService brandService;
    private CategoryService categoryService;
    private ProductService productService;
    private RoleService roleService;
    private UserService userService;

    @Autowired
    public AllServiceAccumulator(BrandService brandService,
                                 CategoryService categoryService,
                                 ProductService productService,
                                 RoleService roleService,
                                 UserService userService) {
        this.brandService = brandService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.roleService = roleService;
        this.userService = userService;
    }

    public BrandService getBrandService() {
        return brandService;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public ProductService getProductService() {
        return productService;
    }

    public RoleService getRoleService() {
        return roleService;
    }

    public UserService getUserService() {
        return userService;
    }
}
