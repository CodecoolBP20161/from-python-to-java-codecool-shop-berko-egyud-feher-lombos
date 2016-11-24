package com.codecool.shop.model;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        LineItemTest.class,
        OrderTest.class,
        ProductTest.class
})

public class JunitTestSuite {
}
