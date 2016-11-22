package com.codecool.shop.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;

public class LineItemTest {

    LineItem lineItem;

    @Mock
    Supplier supplier = new Supplier("name", "description");
    ProductCategory productCategory = new ProductCategory("TestName", "TestDepartment", "TestDescription");
    Product product = new Product("tv", 123.5f, "USD", "description", productCategory, supplier);

    @Before
    public void setUp() throws Exception {
        lineItem = new LineItem(product);
    }

    @After
    public void tearDown() throws Exception {
        lineItem = null;
    }

    @Test
    public void toString_LineItem() throws Exception {
        String result = "id: 0, " +
                "name: tv, " +
                "defaultPrice: 123.500000, " +
                "defaultCurrency: USD, " +
                "productCategory: TestName, " +
                "supplier: name, " +
                "quantity: 1";
        assertEquals(result, lineItem.toString());
    }

}