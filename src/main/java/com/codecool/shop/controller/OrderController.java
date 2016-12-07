package com.codecool.shop.controller;


import com.codecool.shop.dao.implementation.db.LineItemDaoDB;
import com.codecool.shop.dao.implementation.db.OrderDaoDB;

public class OrderController {

    private static OrderDaoDB OrderDaoDB = new OrderDaoDB();
    private static LineItemDaoDB LineItemDaoDB = new LineItemDaoDB();
    private static String STATUS_CHECKED_CART = "UNCHECKED";


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
