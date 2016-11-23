package com.codecool.shop.dao;


import com.codecool.shop.dao.implementation.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)

@Suite.SuiteClasses({
        OrderDaoMemTest.class,
        ProductCategoryDaoMemTest.class,
        ProductDaoMemTest.class,
        SupplierDaoMemTest.class,
        DaoMemRemoveMethodTest.class
})

public class JunitTestSuite {
}
