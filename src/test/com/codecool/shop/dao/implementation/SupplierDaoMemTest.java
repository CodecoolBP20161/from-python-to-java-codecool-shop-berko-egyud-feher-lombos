package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Supplier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;


public class SupplierDaoMemTest {

    SupplierDaoMem supplierDaoMem;

    @Mock
    Supplier supplier = new Supplier("name", "description");

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        supplierDaoMem = SupplierDaoMem.getInstance();
        supplierDaoMem.clear();
        System.out.println("Setting up...");
    }

    @Test
    public void add_Supplier_To_SupplierDaoMem() throws Exception {
        assertEquals(supplier.getId(), supplierDaoMem.getAll().get(0).getId());
        System.out.println("Test add_Supplier_To_SupplierDaoMem passed ...");
    }

    @Test
    public void find_Should_Return_Supplier() throws Exception {
        assertEquals(supplier.toString(), supplierDaoMem.find(supplier.getId()).toString());
        System.out.println("Test find_Should_Return_Supplier passed ...");
    }

    @Test
    public void find_Should_Return_Null() throws Exception {
        assertEquals(null, supplierDaoMem.find(supplier.getId()));
        System.out.println("Test find_Should_Return_Null passed ...");
    }

    @Test
    public void getAll_Should_Return_SupplierList() throws Exception {
        supplierDaoMem.add(supplier);
        assertEquals(Arrays.asList(supplier), supplierDaoMem.getAll());
        System.out.println("Test getAll_Should_Return_SupplierList passed ...");
    }

    @After
    public void tearDown() throws Exception {
        supplierDaoMem = null;
        supplier = null;
        System.out.println("Tearing down to cleaning garbage collection");
    }
}