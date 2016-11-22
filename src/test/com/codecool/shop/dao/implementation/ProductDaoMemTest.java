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
    Product product = new Product();


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        productDao = ProductDaoMem.getInstance();
        System.out.println("Setting up...");
    }


    @Test
    public void add_Product_To_ProductDaoMem() throws Exception {
        productDao.add(product);
        assertEquals(product.getId(), productDao.getAll().get(0).getId());
        System.out.println("Test add_Product_To_ProductDaoMem passed ...");
    }

    @Test
    public void find_Should_Return_Product() throws Exception {
        productDao.add(product);
        assertEquals(product.toString(), productDao.find(product.getId()).toString());
        System.out.println("Test find_Should_Return_Product passed ...");
    }

    @Test
    public void find_Should_Return_Null() throws Exception {
        assertEquals(null, productDao.find(product.getId()));
        System.out.println("Test find_Should_Return_Null passed ...");
    }

    @Test
    public void remove_Product_From_ProductDao() throws Exception {
        productDao.add(product);
        productDao.remove(product.getId());
        assertEquals(null, productDao.find(product.getId()));
        System.out.println("Test remove_Product_From_ProductDao passed ...");
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