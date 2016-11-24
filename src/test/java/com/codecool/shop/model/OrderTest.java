package com.codecool.shop.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class OrderTest {

    Order order;

    @Mock
    Supplier supplier = new Supplier("name", "description");
    ProductCategory productCategory = new ProductCategory("TestName", "TestDepartment", "TestDescription");
    Product product = new Product("tv", 123.5f, "USD", "description", productCategory, supplier);

    @Before
    public void setUp() throws Exception {
        order = new Order();
    }

    @After
    public void tearDown() throws Exception {
        order.remove(product);
    }

    @Test
    public void add_New_Product_To_Order() throws Exception {
        order.add(product);
        String result = "[id: 0, name: tv, defaultPrice: 123.500000, defaultCurrency: USD, productCategory: TestName, supplier: name, quantity: 1]";
        assertEquals(result, order.getItemsToBuy().toString());
    }

    @Test
    public void add_Existing_Product_To_Order() throws Exception {
        for(int i = 0; i < 5; i++)
            order.add(product);
        assertThat(order.getItemsToBuy().toString(), containsString("quantity: 5"));
    }


    @Test
    public void remove() throws Exception {
        order.add(product);
        order.remove(product);
        assertEquals(0, order.getTotalQuantity());
    }

    @Test
    public void toString_Should_Print_Order_Properties() throws Exception {
        order.add(product);
        String result = "id: 0, " +
                "status: CART, " +
                "totalPrice: 123.500000, " +
                "totalQuantity: 1, " +
                "itemsToBuy: [id: 0, name: tv, defaultPrice: 123.500000, defaultCurrency: USD, productCategory: TestName, supplier: name, quantity: 1]";
        assertEquals(result, order.toString());
    }

}