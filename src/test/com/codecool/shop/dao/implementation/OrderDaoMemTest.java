package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;


public class OrderDaoMemTest {

    OrderDaoMem orderDaoMem;

    @Mock
    Order order =  new Order();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        orderDaoMem = OrderDaoMem.getInstance();
        System.out.println("Setting up...");
    }

    @Test
    public void add_Order_To_OrderDaoMem() throws Exception {
        orderDaoMem.add(order);
        assertEquals(order.getId(), orderDaoMem.getAll().get(0).getId());
        System.out.println("Test add_Order_To_OrderDaoMem passed ...");
    }

    @Test
    public void find_Should_Return_Order() throws Exception {
        orderDaoMem.add(order);
        assertEquals(order.toString(), orderDaoMem.find(order.getId()).toString());
        System.out.println("Test find_Should_Return_Order passed ...");
    }

    @Test
    public void find_Should_Return_Null() throws Exception {
        assertEquals(null, orderDaoMem.find(order.getId()));
        System.out.println("Test find_Should_Return_Null passed ...");
    }

    @Test
    public void getAll_Should_Return_OrderList() throws Exception {
        orderDaoMem.add(order);
        assertEquals(Arrays.asList(order), orderDaoMem.getAll());
        System.out.println("Test getAll_Should_Return_OrderList passed ...");
    }

    @Test
    public void getBy() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        orderDaoMem.remove(order.getId());
        order = null;
        System.out.println("Tearing down to cleaning garbage collection");
    }
}