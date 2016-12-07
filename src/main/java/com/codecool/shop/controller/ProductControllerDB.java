package com.codecool.shop.controller;


import com.codecool.shop.dao.implementation.db.*;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Orderable;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.process.CheckoutProcess;
import javassist.NotFoundException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class ProductControllerDB {

    private static ProductDaoDB ProductDB = new ProductDaoDB();
    private static OrderDaoDB OrderDaoDB = new OrderDaoDB();
    private static LineItemDaoDB LineItemDaoDB = new LineItemDaoDB();
    private static ProductCategoryDaoDB ProductCategoryDB = new ProductCategoryDaoDB();
    private static SupplierDaoDB supplierDB = new SupplierDaoDB();

    static Integer categoryId = 1;
    static Integer supplierId = 1;
    static ArrayList<String> savedOrderExamine;
    private static String STATUS_CHECKED_CART;

    // Handle the content of the params HashMap
    private static Map setParams(Request req) throws NotFoundException, SQLException {

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

    // Action for display all or filtered products
    public static ModelAndView renderProducts(Request req, Response res) throws NotFoundException, SQLException {
        Map params = setParams(req);

        if ( req.params(":categoryid") != null ) {
            params.put("category", ProductCategoryDB.find(categoryId));
        }
        if ( req.params(":supplierid") != null ) {
            params.put("supplier", supplierDB.find(supplierId));
        }
        return new ModelAndView(params, "product/index");
    }

    // Action for display cart content
    public static ModelAndView renderCartContent(Request req, Response res) throws NotFoundException, SQLException {
        Map params = setParams(req);
        return new ModelAndView(params, "product/cart");
    }

    // Action for display about us page
    public static ModelAndView renderAboutUs(Request req, Response res) throws NotFoundException, SQLException {
        Map params = setParams(req);
        return new ModelAndView(params, "product/aboutus");
    }

    // Handle the content of the session and set the variables of Order object
    public static String addToCart(Request req, Response res) throws NotFoundException {
        int id = Integer.parseInt(req.params(":id"));
        Orderable order;

        if (req.session().attribute("Cart") == null) {
            order = new Order();
//            System.out.println(req.session().id());
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

        if (req.session().attribute("Cart") == null) {
            order = new Order();
        } else {
            order = req.session().attribute("Cart");
        }

        order.remove(ProductDB.find(id));
        req.session().attribute("Cart", order);
        res.redirect(req.session().attribute("currentUrl"));
        return null;
    }

    public static ModelAndView renderCheckoutProcess(Request req, Response res) throws NotFoundException, SQLException {
        Map params = setParams(req);
        Order order = req.session().attribute("Cart");

        // set the examining list pareters the default (default = user don't push the checkout button yet)
        STATUS_CHECKED_CART = "UNCHECKED";
        savedOrderExamine = new ArrayList<>(Arrays.asList(req.session().id(), STATUS_CHECKED_CART));

        // set userId to order
        // temporary UserId, because the registration form isn't yet ready
        order.setUserSessionId(req.session().id());

        // HOW TO DELETE FROM SESSION ORDER? THERE IS THE METHOD TO DELETE, SET ORDER NEW !! :)
//        req.session().attribute("Cart", order);

        if (savedOrderExamine.get(0).equals(req.session().id()) && savedOrderExamine.get(1).equals(STATUS_CHECKED_CART)) {
            // add order to order's table & set the order id according to the database
            OrderDaoDB.add(order);

//            System.out.println(order.getId());

            // set order's status to "CHECKED"
            CheckoutProcess checkoutProcess = new CheckoutProcess();
            checkoutProcess.process(order);

            for (LineItem lineItem : order.getItemsToBuy()) {
                lineItem.setOrderId(order.getId());
                LineItemDaoDB.add(lineItem);
            }
            savedOrderExamine.set(1, "CHECKED");
        } else if (savedOrderExamine.get(1).equals(STATUS_CHECKED_CART)){
            // update order in order's table
            // update should have to examine the differences between the saved order totalprice and the current total price and the userid and the status
            // i have to make a new list for the new lineitem and with this i have to update the lineitems
//            OrderDaoDB.update(order);
//            LineItemDaoDB.update(lineItem);
        }


        return new ModelAndView(params, "product/checkout");
    }
}