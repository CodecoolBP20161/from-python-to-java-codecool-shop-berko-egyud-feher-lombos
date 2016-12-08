package com.codecool.shop.model.process;

import com.codecool.shop.dao.implementation.db.OrderDaoDB;
import com.codecool.shop.dao.implementation.db.ShippingDataDB;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Orderable;
import com.codecool.shop.model.process.Email.EmailSender;
import javassist.NotFoundException;


public class PayProcess extends AbstractProcess {

    private ShippingDataDB shippingDataDB = ShippingDataDB.getInstance();
    private OrderDaoDB orderDaoDB = OrderDaoDB.getInstance();

    @Override
    protected void action(Orderable item) {
        System.out.println("pay in action");
        item.pay();

        orderDaoDB.update((Order) item);
    }

    @Override
    public void stepAfter(Orderable item) throws NotFoundException {

        String userEmail = shippingDataDB.find(((Order) item).getId()).get(0);
        String userFirstName = shippingDataDB.find(((Order) item).getId()).get(1);
        String userLastName = shippingDataDB.find(((Order) item).getId()).get(2);
        String subject = "Your " + ((Order) item).getId() + " ID  Codecool.com Order";
        String message = "Hello " + userFirstName + " " + userLastName+ " ,\n\n\n" +
                "Thanks for your order. We’ll let you know once your item(s) have dispatched.\n " +
                "Should you need to contact us for any reason, please know that we can give out\n " +
                "order information only to the name and e-mail address associated with your\n " +
                "account. \n\n Thank you again for shopping with us. You can view the status \n" +
                "of your order or make changes to it by visiting Your orders on Codecool.com. \n\n\n" +
                "Order Details:\n" +
                "Ordered items: " + ((Order) item).getItemsToBuy() + "\n " +
                "Total Price: " + ((Order) item).getTotalPrice() + " USD";

        EmailSender.send(userEmail, subject, message);

        System.out.println("stepAfter method...");
    }
}
