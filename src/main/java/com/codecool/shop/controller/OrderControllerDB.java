package com.codecool.shop.controller;


import com.codecool.shop.dao.implementation.db.LineItemDaoDB;
import com.codecool.shop.dao.implementation.db.OrderDaoDB;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.process.CheckoutProcess;
import com.codecool.shop.model.process.PayProcess;
import javassist.NotFoundException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.Map;

public class OrderControllerDB {

    private static OrderDaoDB OrderDaoDB = new OrderDaoDB();
    private static LineItemDaoDB LineItemDaoDB = new LineItemDaoDB();
    private static String STATUS_CHECKED_CART = "UNCHECKED";



    // Action for display cart content
    public static ModelAndView renderCartContent(Request req, Response res) throws NotFoundException, SQLException {
        Map params = Controller.setParams(req);
        return new ModelAndView(params, "product/cart");
    }

    //Action for display checkout page & set order's status to CHECKED
    public static ModelAndView renderCheckoutPage(Request req, Response res) throws NotFoundException, SQLException {
        Map params = Controller.setParams(req);
        Order order = req.session().attribute("Cart");

        // set order's status to "CHECKED"
        CheckoutProcess checkoutProcess = new CheckoutProcess();
        checkoutProcess.process(order);

        return new ModelAndView(params, "product/checkout");
    }

    //Action for display payment page & set order's status to PAID
    public static ModelAndView renderPaymentPage(Request req, Response res) throws NotFoundException, SQLException {
        Map params = Controller.setParams(req);
        Order order = req.session().attribute("Cart");

        // set order's status to "PAID"
        PayProcess payProcess = new PayProcess();
        payProcess.process(order);

        // delete the SessionCartContent
        Controller.removeAllFromSessionCart(req, res);

        return new ModelAndView(params, "product/pay");
    }

    //Action for display after payment page
    public static ModelAndView renderAfterPaymentPage(Request req, Response res) throws NotFoundException, SQLException {
        Map params = Controller.setParams(req);

        // Save the order into DB



        return new ModelAndView(params, "product/afterpay");
    }

}
