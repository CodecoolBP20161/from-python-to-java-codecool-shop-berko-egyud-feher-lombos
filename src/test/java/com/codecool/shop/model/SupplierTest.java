package com.codecool.shop.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class SupplierTest {

    private Supplier supplier;

    @Mock ProductCategory productCategory;
    @Mock Product product;
    @Mock ArrayList<Product> products;


    @Before
    public void setUp() throws Exception {
        supplier = new Supplier(0, "Samsung", "some tvs");
        System.out.println("Setting up...");
    }

    @After
    public void tearDown() throws Exception {
        supplier = null;
        System.out.println("Tearing down to cleaning garbage collection");
    }

    @Test
    public void setProducts_Change_Products() throws Exception {
        supplier.setProducts(products);
        assertEquals(null, supplier.getProducts());
        System.out.println("Test setProducts_Change_Products passed...");
    }

    @Test
    public void getProducts_Should_Return_Products_From_Supplier() throws Exception {
        ArrayList result = new ArrayList<>();
        assertEquals(result, supplier.getProducts());
        System.out.println("Test getProducts_Should_Return_Products_From_Supplier passed...");
    }

    @Test
    public void addProduct_To_Products() throws Exception {
        for (int i = 0; i < 5; i++)
            supplier.addProduct(product);
        assertEquals(5, supplier.getProducts().size());
        System.out.println("Test addProduct_To_Products passed...");
    }

    @Test
    public void toString_Should_Return_Supplier_Properties() throws Exception {
        String result = "id: 0, name: Samsung, description: some tvs";
        assertEquals(result, supplier.toString());
        System.out.println("Test toString_Should_Return_Supplier_Properties passed...");
    }

}
