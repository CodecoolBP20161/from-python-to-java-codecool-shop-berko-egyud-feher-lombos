package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;


public class ProductDaoMemTest {

    ProductDaoMem productDaoMem;

    @Mock
    Product product = new Product();


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        productDaoMem = ProductDaoMem.getInstance();
        System.out.println("Setting up...");
    }


    @Test
    public void add_Product_To_ProductDaoMem() throws Exception {
        productDaoMem.add(product);
        assertEquals(product.getId(), productDaoMem.getAll().get(0).getId());
        System.out.println("Test add_Product_To_ProductDaoMem passed ...");
    }

    @Test
    public void find_Should_Return_Product() throws Exception {
        productDaoMem.add(product);
        assertEquals(product.toString(), productDaoMem.find(product.getId()).toString());
        System.out.println("Test find_Should_Return_Product passed ...");
    }

    @Test
    public void find_Should_Return_Null() throws Exception {
        assertEquals(null, productDaoMem.find(product.getId()));
        System.out.println("Test find_Should_Return_Null passed ...");
    }

    @Test
    public void getAll_Should_Return_ProductList() throws Exception {
        productDaoMem.add(product);
        assertEquals(Arrays.asList(product), productDaoMem.getAll());
        System.out.println("Test getAll_Should_Return_ProductList passed ...");
    }

    @Test
    public void getBy() throws Exception {

    }

    @Test
    public void getBy1() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        productDaoMem.remove(product.getId());
        product = null;
        System.out.println("Tearing down to cleaning garbage collection");
    }

}