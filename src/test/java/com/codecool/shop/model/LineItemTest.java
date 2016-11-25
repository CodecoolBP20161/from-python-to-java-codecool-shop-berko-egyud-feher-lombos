package com.codecool.shop.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;

public class LineItemTest {

    private LineItem lineItem;

    @Mock Supplier supplier = new Supplier("name", "description");
    @Mock ProductCategory productCategory = new ProductCategory("TestName", "TestDepartment", "TestDescription");
    @Mock Product product = new Product("tv", 123.5f, "USD", "description", productCategory, supplier);

    @Before
    public void setUp() throws Exception {
        lineItem = new LineItem(product, 0);
        System.out.println("Setting up...");
    }

    @After
    public void tearDown() throws Exception {
        lineItem = null;
        System.out.println("Tearing down to cleaning garbage collection");
    }

    @Test
    public void getOrderId_Should_Return_OrderId() throws Exception {
        int result = 0;
        assertEquals(result, lineItem.getOrderId());
        System.out.println("Test getOrderId_Should_Return_OrderId passed ...");
    }

    @Test
    public void toString_Should_Return_LineItem_Properties() throws Exception {
        String result = "id: 0, " +
                "name: tv, " +
                "defaultPrice: 123.500000, " +
                "defaultCurrency: USD, " +
                "productCategory: TestName, " +
                "supplier: name, " +
                "quantity: 1";
        assertEquals(result, lineItem.toString());
        System.out.println("Test toString_Should_Return_LineItem_Properties passed ...");
    }

}