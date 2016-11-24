package com.codecool.shop.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;


public class ProductTest {
    Product product;

    @Mock
    Supplier supplier = new Supplier("name", "description");
    ProductCategory productCategory = new ProductCategory("TestName", "TestDepartment", "TestDescription");

    @Before
    public void setUp() throws Exception {
        product = new Product("tv", 123.5f, "USD", "description", productCategory, supplier);
    }

    @After
    public void tearDown() throws Exception {
        product = null;
    }

    @Test
    public void toString_Product() throws Exception {
        String result = "id: 0, " +
                "name: tv, " +
                "defaultPrice: 123.500000, " +
                "defaultCurrency: USD, " +
                "productCategory: TestName, " +
                "supplier: name";
        assertEquals(result, product.toString());
    }

}