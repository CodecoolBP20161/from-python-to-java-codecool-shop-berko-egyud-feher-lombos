package com.codecool.shop.model.process;

import com.codecool.shop.dao.implementation.db.OrderDaoDB;
import com.codecool.shop.dao.implementation.db.ShippingDataDB;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Orderable;
import com.codecool.shop.model.process.Email.EmailSender;
import javassist.NotFoundException;


public class PayProcess extends AbstractProcess {

    private  ShippingDataDB shippingDataDB = new ShippingDataDB();
    private  OrderDaoDB orderDaoDB = new OrderDaoDB();


    @Override
    protected void action(Orderable item) {
        System.out.println("pay in action");
        Order item1 = (Order) item;
        item1.pay();
    }

    @Override
    public void stepAfter(Orderable item) throws NotFoundException {

        String userEmail = shippingDataDB.find(((Order) item).getId());
        System.out.println(userEmail);
        EmailSender.send(userEmail, "please", "please");

        System.out.println("stepAfter method...");
    }
}
