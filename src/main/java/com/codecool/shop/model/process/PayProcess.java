package com.codecool.shop.model.process;

import com.codecool.shop.model.Orderable;


public class PayProcess extends AbstractProcess {
    @Override
    protected void action(Orderable item) {
        System.out.println("pay in action");
        item.checkout();
    }
}
