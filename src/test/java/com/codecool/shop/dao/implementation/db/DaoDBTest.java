package com.codecool.shop.dao.implementation.db;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;


public class DaoDBTest extends DBTestCase {


    // Extend a DBTestCase class
    public DaoDBTest(String name)
    {
        super( name );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.postgresql.Driver" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:postgresql://localhost:5432/postgres" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "lombocska" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "postgres" );
    }

    // Implement methods
    @Before
    public void setUp() throws Exception {
        BuildTestDataBaseTable.build();
        DatabaseExportToXML.createXMLFromDataBase();
        System.out.println("Setting up...");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Tearing down up...");
    }

    @Test
    public void test_Database_With_XML() throws Exception {
        // Fetch database data after executing your code
        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("rendered_html");

        // Load expected data from an XML dataset
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("src/test/java/com/codecool/shop/dao/implementation/db/partial.xml"));
        ITable expectedTable = expectedDataSet.getTable("rendered_html");

        // Assert actual database table match expected table
        assertEquals(expectedTable.getRowCount(), actualTable.getRowCount());
    }

    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/java/com/codecool/shop/dao/implementation/db/partial.xml"));}


    // Implement getSetUpOperation() and getTearDownOperation() methods
    protected DatabaseOperation getSetUpOperation() throws Exception
    {
        return DatabaseOperation.REFRESH;
    }

    protected DatabaseOperation getTearDownOperation() throws Exception
    {
        return DatabaseOperation.NONE;
    }

    // Override method setUpDatabaseConfig(DatabaseConfig config)
    protected void setUpDatabaseConfig(DatabaseConfig config) {
        config.setProperty(DatabaseConfig.PROPERTY_BATCH_SIZE, new Integer(97));
        config.setFeature(DatabaseConfig.FEATURE_BATCHED_STATEMENTS, true);
    }

}