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
import java.util.ArrayList;
import java.util.Arrays;
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

    //Action for display checkout page & set order's status to PAID
    public static ModelAndView renderPaymentPage(Request req, Response res) throws NotFoundException, SQLException {
        Map params = Controller.setParams(req);
        Order order = req.session().attribute("Cart");

        // set order's status to "PAID"
        PayProcess payProcess = new PayProcess();
        payProcess.process(order);

        return new ModelAndView(params, "product/pay");
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

        res.redirect("/pay");
        return null;
    }

    // shipping data saved to session
    public static String saveBankCardData(Request req, Response res) {

        String card_holder_name = req.queryParams("card-holder-name");
        String card_number = req.queryParams("card-number");
        String expiry_month = req.queryParams("expiry-month");
        String expiry_year = req.queryParams("expiry-year");
        String cvv = req.queryParams("cvv");

        ArrayList<String> bankCardDataList =new ArrayList(Arrays.asList(card_holder_name, card_number, expiry_month, expiry_year, cvv));
        req.session().attribute("BankCardDataList", bankCardDataList);

        res.redirect("/");
        return null;
    }



//    public static  saveOrderForOneTimeUser(Request req, Response res) throws NotFoundException, SQLException {
//
//        System.out.println("called the checkout method");
//        Map params = Controller.setParams(req);
//        Order order = req.session().attribute("Cart");
//
//        // set the examining list pareters the default (default = user don't push the checkout button yet)
//        savedOrderExamine = new ArrayList<>(Arrays.asList(req.session().id(), "UNCHECKED"));
//
//        // set userId to order
//        // temporary UserId, because the registration form isn't yet ready
//        order.setUserSessionId(req.session().id());
//
//        // HOW TO DELETE FROM SESSION ORDER? THERE IS THE METHOD TO DELETE, SET ORDER NEW !! :)
////        req.session().attribute("Cart", order);
//
////        if (savedOrderExamine.get(1).equals(STATUS_CHECKED_CART)) {
//        // add order to order's table & set the order id according to the database
//        OrderDaoDB.add(order);
//
////            System.out.println(order.getId());
//
//        // set order's status to "CHECKED"
//        CheckoutProcess checkoutProcess = new CheckoutProcess();
//        checkoutProcess.process(order);
//
////            for (LineItem lineItem : order.getItemsToBuy()) {
////                lineItem.setOrderId(order.getId());
////                LineItemDaoDB.add(lineItem);
////            }
////            STATUS_CHECKED_CART = "CHECKED";
////        } else if (!savedOrderExamine.get(1).equals(STATUS_CHECKED_CART)){
////            System.out.println("Step the double checked user step -.-");
////
////            // examine the changes of user
////            if (order.getTotalPrice() != OrderDaoDB.find(order.getId()).getTotalPrice()) {
////                System.out.println("examine the changes of user");
////                // update order in order's table's totalPrice
////                OrderDaoDB.update(order);
////            }
////
////        }
////        System.out.println("Status checked examine: " + STATUS_CHECKED_CART);
//        return null;
//    }

}
