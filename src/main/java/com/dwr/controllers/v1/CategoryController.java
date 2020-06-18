package com.dwr.controllers.v1;

import com.dwr.api.v1.model.CategoryDTO;
import com.dwr.api.v1.model.CategoryListDTO;
import com.dwr.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {
    private final CategoryService categoryService;
    public static final String BASE_URL = "/api/v1/categories/";

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CategoryListDTO getAllCategories() {
        return new CategoryListDTO(categoryService.getAllCategories());
    }

    @GetMapping("{name}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO getCategoryByName(@PathVariable String name) {
        return categoryService.getCategoryByName(name);
    }


}
