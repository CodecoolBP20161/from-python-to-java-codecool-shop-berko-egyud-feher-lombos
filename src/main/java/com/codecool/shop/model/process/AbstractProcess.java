package com.codecool.shop.model.process;


import com.codecool.shop.model.Orderable;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractProcess {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractProcess.class);

    public void process(Orderable item) throws NotFoundException {
        stepBefore();
        action(item);
        stepAfter(item);
    }

    public void stepBefore() {
        LOGGER.info("stepBefore() method is called");
    }

    protected abstract void action(Orderable item);

    public void stepAfter(Orderable item) throws NotFoundException {
        LOGGER.info("stepAfter() method is called");
    }
}