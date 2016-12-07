package com.codecool.shop.controller;


import com.codecool.shop.dao.implementation.db.*;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Orderable;
import com.codecool.shop.model.process.CheckoutProcess;
import javassist.NotFoundException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;


public class ProductControllerDB {

    private static ProductDaoDB ProductDB = new ProductDaoDB();
    private static OrderDaoDB OrderDaoDB = new OrderDaoDB();
    private static LineItemDaoDB LineItemDaoDB = new LineItemDaoDB();
    private static ProductCategoryDaoDB ProductCategoryDB = new ProductCategoryDaoDB();
    private static SupplierDaoDB supplierDB = new SupplierDaoDB();
    private static ShippingDataDB ShippingDataDB = new ShippingDataDB();

    static Integer categoryId = 1;
    static Integer supplierId = 1;

    // Action for display all or filtered products
    public static ModelAndView renderProducts(Request req, Response res) throws NotFoundException, SQLException {
        Map params = Controller.setParams(req);

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
        Map params = Controller.setParams(req);
        return new ModelAndView(params, "product/cart");
    }

    //Action for display checkout page & set order's status to CHECKED
    public static ModelAndView renderCheckoutProcess(Request req, Response res) throws NotFoundException, SQLException {
        Map params = Controller.setParams(req);
        Order order = req.session().attribute("Cart");

        // set order's status to "CHECKED"
        CheckoutProcess checkoutProcess = new CheckoutProcess();
        checkoutProcess.process(order);

        return new ModelAndView(params, "product/checkout");

    }

    // shipping data saved to session
    public static String saveShippingInfoToSession(Request req, Response res) {

        String first_name = req.queryParams("first_name");
        String last_name = req.queryParams("last_name");
        String email = req.queryParams("email");
        String phone = req.queryParams("phone");
        String adress = req.queryParams("adress");
        String city = req.queryParams("city");
        String state = req.queryParams("state");
        String zip = req.queryParams("zip");
        String comment = req.queryParams("comment");

        ArrayList<String> shippingDataList =new ArrayList(Arrays.asList(first_name, last_name, email, phone, adress, city,state, zip, comment));
        req.session().attribute("ShippingDataList", shippingDataList);

        res.redirect("/cartcontent");
        return null;
    }

    // Action for display about us page
    public static ModelAndView renderAboutUs(Request req, Response res) throws NotFoundException, SQLException {
        Map params = Controller.setParams(req);
        return new ModelAndView(params, "product/aboutus");
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