package com.codecool.shop.dao;



import com.codecool.shop.dao.implementation.db.DaoDBTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)

@Suite.SuiteClasses({
        DaoDBTest.class,
})

public class JunitTestSuite {
}
