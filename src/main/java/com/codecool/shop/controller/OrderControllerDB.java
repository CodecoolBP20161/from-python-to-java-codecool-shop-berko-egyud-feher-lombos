package com.codecool.shop.controller;


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

    // Action for display cart content
    public static ModelAndView renderCartContent(Request req, Response res) throws NotFoundException, SQLException {
        Map params = Controller.setParams(req);
        return new ModelAndView(params, "product/cart");
    }

    //Action for display checkout page & set order's status to CHECKED
    public static ModelAndView renderCheckoutPage(Request req, Response res) throws NotFoundException, SQLException {
        Map params = Controller.setParams(req);
        Order order = req.session().attribute("Cart");
        order.setUserSessionId(req.session().id());

        // set order's status to "CHECKED"
        CheckoutProcess checkoutProcess = new CheckoutProcess();
        checkoutProcess.process(order);

        return new ModelAndView(params, "product/checkout");
    }

    //Action for display payment page & set order's status to PAID
    public static ModelAndView renderPaymentPage(Request req, Response res) throws NotFoundException, SQLException {
        Map params = Controller.setParams(req);


        return new ModelAndView(params, "product/pay");
    }

    //Action for display after payment page
    public static ModelAndView renderAfterPaymentPage(Request req, Response res) throws NotFoundException, SQLException {
        Map params = Controller.setParams(req);
        Order order = req.session().attribute("Cart");

        System.out.println(order);

        // Set order's status to "PAID"
        PayProcess payProcess = new PayProcess();
        payProcess.process(order);

        params.put("navbarButtonsHREF", "removeAllCartContentFromSession");

        return new ModelAndView(params, "product/afterpay");
    }


    public static ModelAndView renderShoppingInformationPage(Request req, Response res) throws NotFoundException, SQLException {
        Map params = Controller.setParams(req);
        Order order = req.session().attribute("Cart");

        return new ModelAndView(params, "product/shippinginformation");
    }

}
