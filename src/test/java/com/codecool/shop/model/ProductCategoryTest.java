package com.codecool.shop.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ProductCategoryTest {

    private ProductCategory productCategory;
    private ProductCategory productCategory2;

    @Mock ArrayList<Product> products;


    @Before
    public void setUp() throws Exception {
        productCategory = new ProductCategory("tv", "Entertainment", "Some tv");
        productCategory2 = new ProductCategory(1, "laptop", "Hardware", "Some laptop");
        System.out.println("Setting up...");
    }

    @After
    public void tearDown() throws Exception {
        productCategory = null;
        productCategory2 = null;
        System.out.println("Tearing down to cleaning garbage collection");
    }

    @Test
    public void getDepartment_Should_Return_Department_Of_ProductCategory() throws Exception {
        String result = "Entertainment";
        assertEquals(result, productCategory.getDepartment());
        System.out.println("Test getDepartment_Should_Return_Department_Of_ProductCategory passed...");
    }

    @Test
    public void setDepartment_Should_Change_Department_Of_ProductCategory() throws Exception {
        String result = "More more Entertainment";
        productCategory2.setDepartment(result);
        assertEquals(result, productCategory2.getDepartment());
        System.out.println("Test setDepartment_Should_Change_Department_Of_ProductCategory passed...");
    }

    @Test
    public void setProducts_Should_Change_Products_Of_ProductCategory() throws Exception {
        productCategory.setProducts(products);
        assertEquals(null, productCategory.getProducts());
        System.out.println("Test setProducts_Change_Products passed...");
    }

    @Test
    public void getProducts_Should_Return_Products_From_ProductCategory() throws Exception {
        ArrayList result = new ArrayList<>();
        assertEquals(result, productCategory.getProducts());
        System.out.println("Test getProducts_Should_Return_Products_From_ProductCategory passed...");
    }


    @Test
    public void toString_Should_Return_ProductCategory_Properties() throws Exception {
        String result = "id: 0,name: tv, department: Entertainment, description: Some tv";
        assertEquals(result, productCategory.toString());
        System.out.println("Test toString_Should_Return_ProductCategory_Properties passed...");
    }

}