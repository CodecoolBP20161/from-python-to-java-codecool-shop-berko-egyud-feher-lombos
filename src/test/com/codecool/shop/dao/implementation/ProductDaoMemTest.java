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
        productDao.add(product);
        System.out.println("Setting up...");
    }


    @Test
    public void add_Product_To_ProductDaoMem() throws Exception {
        assertEquals(product.getId(), productDao.getAll().get(0).getId());
        System.out.println("Test add_Product_To_ProductDaoMem passed ...");
    }

    @Test
    public void find_Should_Return_Product() throws Exception {
        assertEquals(product, productDao.find(product.getId()));
        System.out.println("Test find_Should_Return_Product passed ...");
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
        System.out.println("Tearing down to cleaning garbage collection");
    }

}