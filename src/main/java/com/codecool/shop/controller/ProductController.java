package com.codecool.shop.controller;

import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.*;

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
        Order cart = req.session().attribute("Cart");
        params.put("cart", cart);

        if ( req.params(":categoryid") != null ) {
            params.put("products", productDataStore.getBy(productCategoryDataStore.find(categoryId)));
        }

        if ( req.params(":supplierid") != null ) {
            params.put("products", productDataStore.getBy(productSupplierDataStore.find(supplierId)));
        }

        return new ModelAndView(params, "product/index");
    }

    public static String addToCart(Request req, Response res) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        int id = Integer.parseInt(req.params(":id"));
        Orderable cart;

        if (req.session().attribute("Cart") == null) {
            cart = new Order();
        } else {
            cart = req.session().attribute("Cart");
        }

        cart.add(productDataStore.find(id));
        req.session().attribute("Cart", cart);
        res.redirect("/");
        return null;
    }
}