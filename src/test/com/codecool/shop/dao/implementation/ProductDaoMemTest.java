package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;


public class ProductDaoMemTest {

    ProductDao productDao;

    @Mock
    Product product;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        product = new Product();
        productDao = ProductDaoMem.getInstance();
    }


    @Test
    public void add_Product_To_ProductDaoMem() throws Exception {
        productDao.add(product);
        assertEquals(product.getId(), productDao.getAll().get(0).getId());
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

    @Test
    public void getBy1() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        product = null;
        productDao = null;
    }

}