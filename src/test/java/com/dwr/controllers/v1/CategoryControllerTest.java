package com.dwr.controllers.v1;

import com.dwr.api.v1.model.CategoryDTO;
import com.dwr.services.CategoryService;
import com.dwr.services.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryControllerTest {

    @Mock
    CategoryService categoryService;

    @InjectMocks
    CategoryController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new RestResponseEntityException()).build();
    }

    @Test
    public void getAllCategoriesTest() throws Exception {
        //given
        CategoryDTO category1 = new CategoryDTO();
        category1.setId(1L);
        category1.setName("TestName1");

        CategoryDTO category2 = new CategoryDTO();
        category2.setId(2L);
        category2.setName("TestName2");

        List<CategoryDTO> categories = Arrays.asList(category1, category2);
        //when
        when(categoryService.getAllCategories()).thenReturn(categories);
        //then
        mockMvc.perform(get(CategoryController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories", hasSize(2)));
    }

    @Test
    public void getCategoryByNameTest() throws Exception {
        //given
        CategoryDTO category = new CategoryDTO();
        category.setName("TestName");
        category.setId(1L);

        //when
        when(categoryService.getCategoryByName(anyString())).thenReturn(category);

        //then
        mockMvc.perform(get(CategoryController.BASE_URL + "TestName")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //dollar sign means root content
                .andExpect(jsonPath("$.name", equalTo("TestName")));
    }

    @Test
    public void getByNameNotFound() throws Exception {
        when(categoryService.getCategoryByName(anyString())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CategoryController.BASE_URL + "/Foo")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}





















