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
    ProductCategory productCategory = new ProductCategory("TestName", "TestDepartment", "TestDescription");
    ProductCategory productCategory2 = new ProductCategory("TestName2", "TestDepartment2", "TestDescription2");

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        productCategoryDao = ProductCategoryDaoMem.getInstance();
        productCategoryDao.add(productCategory);
        System.out.println("Setting up...");
    }

    @Test
    public void add_ProductCategory_To_ProductCategoryDaoMem() throws Exception {
        assertEquals(productCategory.getId(), productCategoryDao.getAll().get(0).getId());
        System.out.println(productCategoryDao.getAll());
        System.out.println("Test add_ProductCategory_To_ProductCategoryDaoMem passed ...");
    }

    @Test
    public void find_Should_Return_ProductCategory() throws Exception {
        assertEquals(productCategory.toString(), productCategoryDao.find(productCategory.getId()).toString());
        System.out.println("Test find_Should_Return_ProductCategory passed ...");
    }

    @Test
    public void find_Should_Return_Null() throws Exception {
        assertEquals(null, productCategoryDao.find(productCategory2.getId()));
        System.out.println("Test find_Should_Return_Null passed ...");
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
        productCategory2 = null;
        productCategoryDao = null;
        System.out.println("Tearing down to cleaning garbage collection");
    }
}