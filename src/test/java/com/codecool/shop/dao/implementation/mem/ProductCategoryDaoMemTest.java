package com.codecool.shop.dao.implementation.mem;

import com.codecool.shop.model.ProductCategory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;


public class ProductCategoryDaoMemTest {

    ProductCategoryDaoMem productCategoryDaoMem;

    @Mock
    ProductCategory productCategory = new ProductCategory("TestName", "TestDepartment", "TestDescription");

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        productCategoryDaoMem = ProductCategoryDaoMem.getInstance();
        productCategoryDaoMem.clear();
        System.out.println("Setting up...");
    }

    @Test
    public void add_ProductCategory_To_ProductCategoryDaoMem() throws Exception {
        productCategoryDaoMem.add(productCategory);
        assertEquals(productCategory.getId(), productCategoryDaoMem.getAll().get(0).getId());
        System.out.println("Test add_ProductCategory_To_ProductCategoryDaoMem passed ...");
    }

    @Test
    public void find_Should_Return_ProductCategory() throws Exception {
        productCategoryDaoMem.add(productCategory);
        assertEquals(productCategory.toString(), productCategoryDaoMem.find(productCategory.getId()).toString());
        System.out.println("Test find_Should_Return_ProductCategory passed ...");
    }

    @Test
    public void find_Should_Return_Null() throws Exception {
        assertEquals(null, productCategoryDaoMem.find(productCategory.getId()));
        System.out.println("Test find_Should_Return_Null passed ...");
    }

    @Test
    public void getAll_Should_Return_ProductCategoryList() throws Exception {
        productCategoryDaoMem.add(productCategory);
        assertEquals(Arrays.asList(productCategory), productCategoryDaoMem.getAll());
        System.out.println("Test getAll_Should_Return_ProductCategoryList passed ...");
    }

    @Test
    public void remove_ProductCategory_From_ProductCategoryDaoMem() throws Exception {
        productCategoryDaoMem.add(productCategory);
        productCategoryDaoMem.remove(productCategory.getId());
        assertEquals(null, productCategoryDaoMem.find(productCategory.getId()));
        System.out.println("Test remove_ProductCategory_From_ProductCategoryDaoMem passed ...");
    }

    @After
    public void tearDown() throws Exception {
        productCategoryDaoMem = null;
        productCategory = null;
        System.out.println("Tearing down to cleaning garbage collection");
    }
}