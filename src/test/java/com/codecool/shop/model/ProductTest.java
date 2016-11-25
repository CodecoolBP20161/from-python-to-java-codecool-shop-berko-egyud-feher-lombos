package com.codecool.shop.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;


public class ProductTest {
    private Product product;
    private Product product2;

    @Mock Supplier supplier = new Supplier("name", "description");
    @Mock ProductCategory productCategory = new ProductCategory("TestName", "TestDepartment", "TestDescription");

    @Before
    public void setUp() throws Exception {
        product = new Product("tv", 123.5f, "USD", "description", productCategory, supplier);
        product2 = new Product(0, "tv", 123.5f, "USD", "description", productCategory, supplier);
        System.out.println("Setting up...");
    }

    @After
    public void tearDown() throws Exception {
        product = null;
        product2 = null;
        System.out.println("Tearing down to cleaning garbage collection");
    }

    @Test
    public void getPrice_Of_Product_With_Currency() throws Exception {
        String result = product.defaultPrice + " " + product.defaultCurrency;
        assertEquals(result, product.getPrice());
        System.out.println("Test getPrice_Of_Product_With_Currency passed ...");
    }

    @Test
    public void toString_Should_Return_Product_Properties() throws Exception {
        String result = "id: 0, " +
                "name: tv, " +
                "defaultPrice: 123.500000, " +
                "defaultCurrency: USD, " +
                "productCategory: TestName, " +
                "supplier: name";
        assertEquals(result, product.toString());
        System.out.println("Test getPrice_Of_Product_With_Currency passed ...");
    }

}