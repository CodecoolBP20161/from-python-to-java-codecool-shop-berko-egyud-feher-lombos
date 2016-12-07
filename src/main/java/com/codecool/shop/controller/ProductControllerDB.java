package com.codecool.shop.controller;


import com.codecool.shop.dao.implementation.db.*;
import javassist.NotFoundException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.Map;


public class ProductControllerDB {

    private static ProductCategoryDaoDB ProductCategoryDB = new ProductCategoryDaoDB();
    private static SupplierDaoDB supplierDB = new SupplierDaoDB();

    static Integer categoryId = 1;
    static Integer supplierId = 1;

    // Action for display all or filtered products
    public static ModelAndView renderProducts(Request req, Response res) throws NotFoundException, SQLException {
        Map params = Controller.setParams(req);

        if ( req.params(":categoryid") != null ) {
            params.put("category", ProductCategoryDB.find(categoryId));
        }
        if ( req.params(":supplierid") != null ) {
            params.put("supplier", supplierDB.find(supplierId));
        }
        return new ModelAndView(params, "product/index");
    }

    // Action for display about us page
    public static ModelAndView renderAboutUs(Request req, Response res) throws NotFoundException, SQLException {
        Map params = Controller.setParams(req);
        return new ModelAndView(params, "product/aboutus");
    }

}