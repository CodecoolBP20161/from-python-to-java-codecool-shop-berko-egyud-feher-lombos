package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

public class DaoMemRemoveTest {

    OrderDaoMem orderDaoMem;
    ProductCategoryDaoMem productCategoryDaoMem;
    ProductDaoMem productDaoMem;
    SupplierDaoMem supplierDaoMem;

    @Mock
    Order order =  new Order();
    ProductCategory productCategory = new ProductCategory("CategoryName", "CategoryDepartment", "CategoryDescription");
    Product product = new Product();
    Supplier supplier = new Supplier("SupplierName", "SupplierDescription");

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        orderDaoMem = OrderDaoMem.getInstance();
        productCategoryDaoMem = ProductCategoryDaoMem.getInstance();
        productDaoMem = ProductDaoMem.getInstance();
        supplierDaoMem = SupplierDaoMem.getInstance();
        System.out.println("Setting up...");
    }

    @Test
    public void remove_Order_From_OrderDaoMem() throws Exception {
        orderDaoMem.add(order);
        orderDaoMem.remove(order.getId());
        assertEquals(null, orderDaoMem.find(order.getId()));
        System.out.println("Test remove_Order_From_OrderDaoMem passed ...");
    }

    @Test
    public void remove_ProductCategory_From_ProductCategoryDaoMem() throws Exception {
        productCategoryDaoMem.add(productCategory);
        productCategoryDaoMem.remove(productCategory.getId());
        assertEquals(null, productCategoryDaoMem.find(productCategory.getId()));
        System.out.println("Test remove_ProductCategory_From_ProductCategoryDaoMem passed ...");
    }

    @Test
    public void remove_Product_From_ProductDao() throws Exception {
        productDaoMem.add(product);
        productDaoMem.remove(product.getId());
        assertEquals(null, productDaoMem.find(product.getId()));
        System.out.println("Test remove_Product_From_ProductDao passed ...");
    }

    @Test
    public void remove_Supplier_From_SupplierDao() throws Exception {
        supplierDaoMem.add(supplier);
        supplierDaoMem.remove(supplier.getId());
        assertEquals(null, supplierDaoMem.find(supplier.getId()));
        System.out.println("Test remove_Supplier_From_SupplierDao passed ...");
    }

    @After
    public void tearDown() throws Exception {
        order = null;
        System.out.println("Tearing down to cleaning garbage collection");
    }
}