package com.codecool.shop.model.process;


import com.codecool.shop.model.Orderable;

public class CheckoutProcess extends AbstractProcess {

    public void action(Orderable item) {
        System.out.println("checkout in action");
        item.checkout();
    }
}
