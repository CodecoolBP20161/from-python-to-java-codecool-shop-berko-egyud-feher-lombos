package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;


public class OrderDaoMemTest {

    OrderDao orderDao;

    @Mock
    Order order =  new Order();
    Order order2 = new Order();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        orderDao = OrderDaoMem.getInstance();
        orderDao.add(order);
        System.out.println("Setting up...");
    }

    @Test
    public void add_Order_To_OrderDaoMem() throws Exception {
        assertEquals(order.getId(), orderDao.getAll().get(0).getId());
        System.out.println("Test add_Order_To_OrderDaoMem passed ...");
    }

    @Test
    public void find_Should_Return_Order() throws Exception {
        assertEquals(order.toString(), orderDao.find(order.getId()).toString());
        System.out.println("Test find_Should_Return_Order passed ...");
    }

    @Test
    public void find_Should_Return_Null() throws Exception {
        assertEquals(null, orderDao.find(order2.getId()));
    }

    @Test
    public void remove() throws Exception {

    }

    @Test
    public void getAll() throws Exception {

    }

    @Test
    public void getBy() throws Exception {

    }


    @After
    public void tearDown() throws Exception {
        order = null;
        order2 = null;
        orderDao = null;
        System.out.println("Tearing down to cleaning garbage collection");
    }
}