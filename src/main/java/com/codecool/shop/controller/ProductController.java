package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.*;

import spark.Request;
import spark.Response;
import spark.ModelAndView;

import java.util.HashMap;
import java.util.Map;


public class ProductController {

    private static ProductDao productDataStore = ProductDaoMem.getInstance();
    private static ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
    private static SupplierDao productSupplierDataStore = SupplierDaoMem.getInstance();
    static Integer categoryId = 1;
    static Integer supplierId = 1;

    // Handle the content of the params HashMap
    public static Map setParams(Request req) {
        Map params = new HashMap<>();
        params.put("category", new ProductCategory("All Products", "All Products", "All Products"));
        params.put("categories", productCategoryDataStore.getAll());
        params.put("products", productDataStore.getAll());
        params.put("suppliers", productSupplierDataStore.getAll());
        req.session().attribute("currentUrl", "/");
        if ( req.params(":categoryid") != null ) {
            categoryId = Integer.parseInt(req.params(":categoryid"));
            params.put("products", productDataStore.getBy(productCategoryDataStore.find(categoryId)));
            req.session().attribute("currentUrl", "/category/" + req.params(":categoryid"));

        }
        else if ( req.params(":supplierid") != null ) {
            supplierId = Integer.parseInt(req.params(":supplierid"));
            params.put("products", productDataStore.getBy(productSupplierDataStore.find(supplierId)));
            req.session().attribute("currentUrl", "/supplier/" + req.params(":supplierid"));
        }
        Order cart = req.session().attribute("Cart");
        params.put("cart", cart);
        return params;
    }

    // Action for display all or filtered products
    public static ModelAndView renderProducts(Request req, Response res) {
        Map params = setParams(req);
        if ( req.params(":categoryid") != null ) {
            params.put("category", productCategoryDataStore.find(categoryId));
        }
        if ( req.params(":supplierid") != null ) {
            params.put("supplier", productSupplierDataStore.find(supplierId));
        }
        return new ModelAndView(params, "product/index");
    }

    // Action for display cart content
    public static ModelAndView renderCartContent(Request req, Response res) {
        Map params = setParams(req);
        return new ModelAndView(params, "product/cart");
    }

    // Handle the content of the session and set the variables of Order object
    public static String addToCart(Request req, Response res) {
        int id = Integer.parseInt(req.params(":id"));
        Orderable cart;

        if (req.session().attribute("Cart") == null) {
            cart = new Order();
        } else {
            cart = req.session().attribute("Cart");
        }

        cart.add(productDataStore.find(id));
        req.session().attribute("Cart", cart);
        res.redirect(req.session().attribute("currentUrl"));
        return null;
    }
}