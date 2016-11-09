package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import spark.Request;
import spark.Response;
import spark.ModelAndView;

import java.util.HashMap;
import java.util.Map;

public class ProductController {

    public static ModelAndView renderProducts(Request req, Response res) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao productSupplierDataStore = SupplierDaoMem.getInstance();

        Map params = new HashMap<>();
        Integer categoryId = 1;
        Integer supplierId = 1;
        params.put("category", new ProductCategory("All Products", "All Products", "All Products"));

        if ( req.params(":categoryid") != null ) {
            categoryId = Integer.parseInt(req.params(":categoryid"));
            params.put("category", productCategoryDataStore.find(categoryId));
        }
        if ( req.params(":supplierid") != null ) {
            supplierId = Integer.parseInt(req.params(":supplierid"));
            params.put("supplier", productSupplierDataStore.find(supplierId));
        }

        params.put("categories", productCategoryDataStore.getAll());
        params.put("products", productDataStore.getAll());
        params.put("suppliers", productSupplierDataStore.getAll());

        if ( req.params(":categoryid") != null ) {
            params.put("products", productDataStore.getBy(productCategoryDataStore.find(categoryId)));
        }

        if ( req.params(":supplierid") != null ) {
            params.put("products", productDataStore.getBy(productSupplierDataStore.find(supplierId)));
        }

        return new ModelAndView(params, "product/index");
    }

}
