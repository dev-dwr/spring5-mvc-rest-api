package com.dwr.api.v1.mapper;

import com.dwr.api.v1.model.CategoryDTO;
import com.dwr.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryMapperTest {

    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void categoryToCategoryDTO() {
        //given
        Category category = new Category();
        category.setName("Test");
        category.setId(1L);

        //when
        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        //then
        assertEquals(Long.valueOf(1), categoryDTO.getId());
        assertEquals("Test", categoryDTO.getName());
    }
}