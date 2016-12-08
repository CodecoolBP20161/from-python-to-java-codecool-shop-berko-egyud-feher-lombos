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

    private static ProductCategoryDaoDB ProductCategoryDB = ProductCategoryDaoDB.getInstance();
    private static ProductDaoDB ProductDB = new ProductDaoDB();
    private static SupplierDaoDB SupplierDB = new SupplierDaoDB();

    static Integer categoryId = 1;
    static Integer supplierId = 1;

    // Action for display all with opportunities of pagination
    public static ModelAndView renderProducts(Request req, Response res) throws NotFoundException, SQLException {
        Map params = Controller.setParams(req);

        // pagination handling
        params.put("pageNumberList", ProductDB.getPageNumberList((int) Math.ceil(ProductDB.getAll().size()/10.0)));
        params.put("lastPageNumber", (int) Math.ceil(ProductDB.getAll().size()/10.0));

        // products put to params according to the paginationNumber
        if (req.queryParams("paginationNumber") == null) {
            params.put("products", ProductDB.getProductByPagination(1));
            params.put("paginationNumber", 1);
        } else if (req.queryParams("paginationNumber") != null) {
            int paginationNumber = Integer.parseInt(req.queryParams("paginationNumber"));
            // to set the pagination button active/disabled
            params.put("paginationNumber", paginationNumber);

            // dynamic page paginaton handling
            if (paginationNumber == 1){
                params.put("products", ProductDB.getProductByPagination((0)));
            } else if (paginationNumber > 1) {
                params.put("products", ProductDB.getProductByPagination((paginationNumber*10)-9));
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
            categoryId = Integer.parseInt(req.params(":categoryid"));
            params.put("products", ProductDB.getBy(ProductCategoryDB.find(categoryId)));
            req.session().attribute("currentUrl", "/category/" + req.params(":categoryid"));
        }
        else if ( req.params(":supplierid") != null ) {
            supplierId = Integer.parseInt(req.params(":supplierid"));
            params.put("products", ProductDB.getBy(SupplierDB.find(supplierId)));
            req.session().attribute("currentUrl", "/supplier/" + req.params(":supplierid"));
        }

        if ( req.params(":categoryid") != null ) {
            params = Controller.setParams(req);
            params.put("category", ProductCategoryDB.find(categoryId));
        }
        if ( req.params(":supplierid") != null ) {
            params = Controller.setParams(req);
            params.put("supplier", SupplierDB.find(supplierId));
        }
        return new ModelAndView(params, "product/index");
    }

    // Action for display about us page
    public static ModelAndView renderAboutUs(Request req, Response res) throws NotFoundException, SQLException {
        Map params = Controller.setParams(req);
        return new ModelAndView(params, "product/aboutus");
    }

}