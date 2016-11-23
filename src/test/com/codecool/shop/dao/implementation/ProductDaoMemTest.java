package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


public class ProductDaoMemTest {

    ProductDaoMem productDaoMem;

    @Mock
    Product product = new Product();


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        productDaoMem = ProductDaoMem.getInstance();
        productDaoMem.clear();
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
    public void getBy_Should_Return_OrderList_By_Supplier() throws Exception {
        productDaoMem.add(product);

        Supplier supplier = new Supplier("SupplierName", "SupplierDescription");
        product.setSupplier(supplier);
        when(product.getSupplier()).thenReturn(supplier);
        assertEquals(supplier, product.getSupplier());

        assertEquals(Arrays.asList(product), productDaoMem.getBy(supplier));

        System.out.println("Test getBy_Should_Return_OrderList_By_Supplier passed ...");
    }

    @Test
    public void getBy_Should_Return_OrderList_By_Category() throws Exception {
        productDaoMem.add(product);

        ProductCategory productCategory = new ProductCategory("ProductCategoryName", "ProductCategoryDepartment", "ProductCategoryDescription");
        product.setProductCategory(productCategory);
        when(product.getProductCategory()).thenReturn(productCategory);
        assertEquals(productCategory, product.getProductCategory());

        assertEquals(Arrays.asList(product), productDaoMem.getBy(productCategory));

        System.out.println("Test getBy_Should_Return_OrderList_By_Category passed ...");
    }

    @Test
    public void remove_Product_From_ProductDao() throws Exception {
        productDaoMem.add(product);
        productDaoMem.remove(product.getId());
        assertEquals(null, productDaoMem.find(product.getId()));
        System.out.println("Test remove_Product_From_ProductDao passed ...");
    }

    @After
    public void tearDown() throws Exception {
        productDaoMem = null;
        product = null;
        System.out.println("Tearing down to cleaning garbage collection");
    }

}