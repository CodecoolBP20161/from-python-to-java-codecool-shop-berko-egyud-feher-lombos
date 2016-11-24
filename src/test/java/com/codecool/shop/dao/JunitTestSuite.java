package com.codecool.shop.dao;


import com.codecool.shop.dao.implementation.mem.OrderDaoMemTest;
import com.codecool.shop.dao.implementation.mem.ProductCategoryDaoMemTest;
import com.codecool.shop.dao.implementation.mem.ProductDaoMemTest;
import com.codecool.shop.dao.implementation.mem.SupplierDaoMemTest;
import com.codecool.shop.dao.implementation.db.LineItemDaoDBTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)

@Suite.SuiteClasses({
        OrderDaoMemTest.class,
        ProductCategoryDaoMemTest.class,
        ProductDaoMemTest.class,
        SupplierDaoMemTest.class,
        LineItemDaoDBTest.class,
})

public class JunitTestSuite {
}
