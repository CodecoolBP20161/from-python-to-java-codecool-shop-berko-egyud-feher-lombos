package com.codecool.shop.model.process;


import com.codecool.shop.dao.implementation.db.LineItemDaoDB;
import com.codecool.shop.dao.implementation.db.OrderDaoDB;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Orderable;

public class CheckoutProcess extends AbstractProcess {
    private OrderDaoDB orderDaoDB = OrderDaoDB.getInstance();

    LineItemDaoDB lineItemDaoDB = LineItemDaoDB.getInstance();

    @Override
    public void action(Orderable item) {
        System.out.println("checkout in action");
        item.checkout();

        orderDaoDB.add((Order) item);
        for (LineItem lineItem : ((Order) item).getItemsToBuy()) {
            lineItem.setOrderId(((Order) item).getId());
            lineItemDaoDB.add(lineItem);
        }
    }
}
