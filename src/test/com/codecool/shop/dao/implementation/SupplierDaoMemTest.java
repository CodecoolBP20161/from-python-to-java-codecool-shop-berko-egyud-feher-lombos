package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;


public class SupplierDaoMemTest {

    SupplierDao supplierDao;

    @Mock
    Supplier supplier;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        supplier = new Supplier("name", "description");
        supplierDao = SupplierDaoMem.getInstance();
        System.out.println("Setting up...");
    }

    @Test
    public void add_Supplier_To_SupplierDaoMem() throws Exception {
        supplierDao.add(supplier);
        assertEquals(supplier.getId(), supplierDao.getAll().get(0).getId());
        System.out.println("Test add_Order_To_OrderDaoMem passed ...");
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

    @After
    public void tearDown() throws Exception {
        supplier = null;
        supplierDao = null;
        System.out.println("Tearing down to cleaning garbage collection");
    }
}