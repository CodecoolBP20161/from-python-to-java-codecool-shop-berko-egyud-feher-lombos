package com.codecool.shop.controller;


import com.codecool.shop.dao.implementation.db.ProductCategoryDaoDB;
import com.codecool.shop.dao.implementation.db.ProductDaoDB;
import com.codecool.shop.dao.implementation.db.SupplierDaoDB;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Orderable;
import com.codecool.shop.model.ProductCategory;
import javassist.NotFoundException;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Controller {

    private static ProductDaoDB ProductDB = new ProductDaoDB();
    private static ProductCategoryDaoDB ProductCategoryDB = new ProductCategoryDaoDB();
    private static SupplierDaoDB supplierDB = new SupplierDaoDB();

    static Integer categoryId = 1;
    static Integer supplierId = 1;

    // Handle the content of the params HashMap
    static Map setParams(Request req) throws NotFoundException, SQLException {

        Map params = new HashMap<>();

        params.put("category", new ProductCategory("All Products", "All Products", "All Products"));
        params.put("categories", ProductCategoryDB.getAll());
        params.put("products", ProductDB.getAll());
        params.put("suppliers", supplierDB.getAll());

        req.session().attribute("currentUrl", "/");

        if ( req.params(":categoryid") != null ) {
            categoryId = Integer.parseInt(req.params(":categoryid"));
            params.put("products", ProductDB.getBy(ProductCategoryDB.find(categoryId)));
            req.session().attribute("currentUrl", "/category/" + req.params(":categoryid"));

        }
        else if ( req.params(":supplierid") != null ) {
            supplierId = Integer.parseInt(req.params(":supplierid"));
            params.put("products", ProductDB.getBy(supplierDB.find(supplierId)));
            req.session().attribute("currentUrl", "/supplier/" + req.params(":supplierid"));
        }
        else if ( req.url().equals("http://localhost:8888/cartcontent")) {
            req.session().attribute("currentUrl", "/cartcontent");
        }

        Order cart = req.session().attribute("Cart");
        params.put("cart", cart);
        return params;

    }


    // Handle the content of the session and set the variables of Order object
    public static String addToCart(Request req, Response res) throws NotFoundException {
        int id = Integer.parseInt(req.params(":id"));
        Orderable order;

        if (req.session().attribute("Cart") == null) {
            order = new Order();
        } else {
            order = req.session().attribute("Cart");
        }

        order.add(ProductDB.find(id));
        req.session().attribute("Cart", order);
        res.redirect(req.session().attribute("currentUrl"));
        return null;
    }

    // Handle the content of the session and set the variables of Order object
    public static String removeFromCart(Request req, Response res) throws NotFoundException {
        int id = Integer.parseInt(req.params(":id"));
        Orderable order;
        order = req.session().attribute("Cart");

        // remove one product from session
        order.remove(ProductDB.find(id));
        req.session().attribute("Cart", order);
        res.redirect(req.session().attribute("currentUrl"));
        return null;
    }

    // Handle the content of the session and set the variables of Order object
    public static String removeAllFromCart(Request req, Response res) throws NotFoundException {
        Order order = null;
        req.session().attribute("Cart", order);
        res.redirect(req.session().attribute("currentUrl"));
        return null;
    }
}
