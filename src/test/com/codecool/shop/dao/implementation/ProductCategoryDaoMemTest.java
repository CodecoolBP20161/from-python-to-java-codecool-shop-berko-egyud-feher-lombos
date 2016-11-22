package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;


public class ProductCategoryDaoMemTest {

    ProductCategoryDao productCategoryDao;

    @Mock
    ProductCategory productCategory;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        productCategory = new ProductCategory("TestName", "TestDepartment", "TestDescription");
        productCategoryDao = ProductCategoryDaoMem.getInstance();
    }

    @Test
    public void add_ProductCategory_To_ProductCategoryDaoMem() throws Exception {
        productCategoryDao.add(productCategory);
        assertEquals(productCategory.getId(), productCategoryDao.getAll().get(0).getId());
    }

    @Test
    public void find() throws Exception {

    }

    @Test
    public void remove() throws Exception {

    }

    @Test
    public void getAll() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        productCategory = null;
        productCategoryDao = null;
    }
}