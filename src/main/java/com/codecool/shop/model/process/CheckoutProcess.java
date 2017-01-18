package com.codecool.shop.model.process;


import com.codecool.shop.dao.implementation.db.LineItemDaoDB;
import com.codecool.shop.dao.implementation.db.OrderDaoDB;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Orderable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckoutProcess extends AbstractProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutProcess.class);

    private OrderDaoDB orderDaoDB = OrderDaoDB.getInstance();
    private LineItemDaoDB lineItemDaoDB = LineItemDaoDB.getInstance();

    @Override
    public void action(Orderable item) {
        LOGGER.info("action() method is called");
        item.checkout();

        orderDaoDB.add((Order) item);
        for (LineItem lineItem : ((Order) item).getItemsToBuy()) {
            lineItem.setOrderId(((Order) item).getId());
            lineItemDaoDB.add(lineItem);
        }
    }
}
