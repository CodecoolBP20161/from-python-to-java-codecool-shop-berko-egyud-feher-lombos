package com.codecool.shop.model.process;


import com.codecool.shop.model.Orderable;
import javassist.NotFoundException;

public abstract class AbstractProcess {

    public void process(Orderable item) throws NotFoundException {
        stepBefore();
        action(item);
        stepAfter(item);
    }

    public void stepBefore() {
        System.out.println("saving to database");
    }

    protected abstract void action(Orderable item);

    public void stepAfter(Orderable item) throws NotFoundException {
        System.out.println("stepAfter?");
    }
}