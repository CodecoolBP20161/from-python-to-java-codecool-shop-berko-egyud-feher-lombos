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
    Supplier supplier = new Supplier("name", "description");
    Supplier supplier2 = new Supplier("name", "description");

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        supplierDao = SupplierDaoMem.getInstance();
        supplierDao.add(supplier);
        System.out.println("Setting up...");
    }

    @Test
    public void add_Supplier_To_SupplierDaoMem() throws Exception {
        assertEquals(supplier.getId(), supplierDao.getAll().get(0).getId());
        System.out.println("Test add_Supplier_To_SupplierDaoMem passed ...");
    }

    @Test
    public void find_Should_Return_Supplier() throws Exception {
        assertEquals(supplier.toString(), supplierDao.find(supplier.getId()).toString());
        System.out.println("Test find_Should_Return_Supplier passed ...");
    }

    @Test
    public void find_Should_Return_Null() throws Exception {
        assertEquals(null, supplierDao.find(supplier2.getId()));
        System.out.println("Test find_Should_Return_Null passed ...");
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