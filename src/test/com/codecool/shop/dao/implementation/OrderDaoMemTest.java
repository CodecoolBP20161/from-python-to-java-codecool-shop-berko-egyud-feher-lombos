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
    Order order;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        order = new Order();
        orderDao = OrderDaoMem.getInstance();
    }
    @Test
    public void add_Order_To_OrderDaoMem() throws Exception {
        orderDao.add(order);
        assertEquals(order.getId() ,orderDao.getAll().get(0).getId());
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

    @Test
    public void getBy() throws Exception {

    }


    @After
    public void tearDown() throws Exception {
        order = null;
        orderDao = null;
    }
}