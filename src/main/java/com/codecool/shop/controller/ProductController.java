package com.codecool.shop.controller;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;
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

    public static Map setParams(Request req) {
        Map params = new HashMap<>();
        params.put("category", new ProductCategory("All Products", "All Products", "All Products"));
        params.put("categories", productCategoryDataStore.getAll());
        params.put("products", productDataStore.getAll());
        params.put("suppliers", productSupplierDataStore.getAll());
        if ( req.params(":categoryid") != null ) {
            categoryId = Integer.parseInt(req.params(":categoryid"));
            params.put("products", productDataStore.getBy(productCategoryDataStore.find(categoryId)));
        }
        if ( req.params(":supplierid") != null ) {
            supplierId = Integer.parseInt(req.params(":supplierid"));
            params.put("products", productDataStore.getBy(productSupplierDataStore.find(supplierId)));
        }
        Order cart = req.session().attribute("Cart");
        params.put("cart", cart);
        return params;
    }

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

    public static ModelAndView renderCartContent(Request req, Response res) {
        Map params = setParams(req);
        return new ModelAndView(params, "product/cart");
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