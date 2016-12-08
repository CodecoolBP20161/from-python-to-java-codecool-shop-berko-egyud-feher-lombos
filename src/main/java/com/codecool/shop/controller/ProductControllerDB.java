package com.codecool.shop.controller;


import com.codecool.shop.dao.implementation.db.ProductCategoryDaoDB;
import com.codecool.shop.dao.implementation.db.ProductDaoDB;
import com.codecool.shop.dao.implementation.db.SupplierDaoDB;
import javassist.NotFoundException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.Map;


public class ProductControllerDB {

    private static ProductCategoryDaoDB ProductCategoryDB = new ProductCategoryDaoDB();
    private static ProductDaoDB ProductDB = new ProductDaoDB();
    private static SupplierDaoDB supplierDB = new SupplierDaoDB();

    static Integer categoryId = 1;
    static Integer supplierId = 1;

    // Action for display all with opportunities of pagination
    public static ModelAndView renderProducts(Request req, Response res) throws NotFoundException, SQLException {
        Map params = Controller.setParams(req);

        // products put to params according to the paginationNumber
        if (req.queryParams("paginationNumber") == null) {
            params.put("products", ProductDB.getProductByPagination(1));
            params.put("paginationNumber", 1);
        } else if (req.queryParams("paginationNumber") != null) {
            int paginationNumber = Integer.parseInt(req.queryParams("paginationNumber"));

            // to set the pagination button active/disabled
            params.put("paginationNumber", paginationNumber);

            if (paginationNumber == 1) {
                params.put("products", ProductDB.getProductByPagination(1));
            } else if (paginationNumber == 2) {
                params.put("products", ProductDB.getProductByPagination(11));
            } else if (paginationNumber == 3) {
                params.put("products", ProductDB.getProductByPagination(21));
            }
        }

        // to examine with thymeleaf the page
        params.put("paginationExamine", "INDEX");
        return new ModelAndView(params, "product/index");
    }

    // Action for display filtered products
    public static ModelAndView renderFilteredProducts(Request req, Response res) throws NotFoundException, SQLException {
        Map params = Controller.setParams(req);

        if ( req.params(":categoryid") != null ) {
            params = Controller.setParams(req);
            params.put("category", ProductCategoryDB.find(categoryId));
        }
        if ( req.params(":supplierid") != null ) {
            params = Controller.setParams(req);
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