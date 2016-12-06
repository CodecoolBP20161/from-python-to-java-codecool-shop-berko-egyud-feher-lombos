package com.codecool.shop.model.process;


import com.codecool.shop.model.Orderable;

public abstract class AbstractProcess {

    public void process(Orderable item) {
        stepBefore();
        action(item);
        stepAfter();
    }

    public void stepBefore() {
        System.out.println("saving to database");
    }

    protected abstract void action(Orderable item);

    public void stepAfter() {
        System.out.println("sending email...");
    }
}