package com.codecool.shop.controller.rendered_page;


import com.codecool.shop.controller.event_controller.session_controller.SessionEventController;
import com.codecool.shop.dao.implementation.db.ProductCategoryDaoDB;
import com.codecool.shop.dao.implementation.db.ProductDaoDB;
import com.codecool.shop.dao.implementation.db.SupplierDaoDB;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.Map;


public class ProductControllerDB {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductControllerDB.class);

    private static ProductCategoryDaoDB productCategoryDB = ProductCategoryDaoDB.getInstance();
    private static ProductDaoDB productDB = ProductDaoDB.getInstance();
    private static SupplierDaoDB supplierDB = SupplierDaoDB.getInstance();

    static Integer categoryId = 1;
    static Integer supplierId = 1;


    /**
     * Render the index.html form. The index.html displays all products without any filter.
     * Pagination implemented here, 10 item per page displayed.
     * @param req - Spark Request
     * @param res - Spark Response
     * @return ModelAndView to render index.html
     * @throws NotFoundException
     * @throws SQLException
     */
    public static ModelAndView renderProducts(Request req, Response res) throws NotFoundException, SQLException {
        Map params = SessionEventController.setParams(req);
        LOGGER.info("renderProducts() method is called.");

        // pagination handling
        params.put("pageNumberList", productDB.getPageNumberList((int) Math.ceil(productDB.getAll().size()/10.0)));
        params.put("lastPageNumber", (int) Math.ceil(productDB.getAll().size()/10.0));

        // products put to params according to the paginationNumber
        if (req.queryParams("paginationNumber") == null) {
            params.put("products", productDB.getProductByPagination(1));
            params.put("paginationNumber", 1);
            LOGGER.debug("First-loaded page get a number for pagination.");

        } else if (req.queryParams("paginationNumber") != null) {
            LOGGER.debug("Page examine the number of pagination from URL with queryParams method.");

            int paginationNumber = Integer.parseInt(req.queryParams("paginationNumber"));
            // to set the pagination button active/disabled
            params.put("paginationNumber", paginationNumber);

            // dynamic page paginaton handling
            if (paginationNumber == 1){
                params.put("products", productDB.getProductByPagination((0)));
            } else if (paginationNumber > 1) {
                params.put("products", productDB.getProductByPagination((paginationNumber*10)-9));
            }
        }

        params.put("authenticated", req.session().attribute("authenticated"));
        params.put("username", req.session().attribute("username"));

        // to examine with thymeleaf the page
        params.put("paginationExamine", "INDEX");
        return new ModelAndView(params, "rendered_html/index");
    }


    /**
     * Render the index.html form. The index.html displays the products filtered by category or supplier.
     * @param req - Spark Request
     * @param res - Spark Response
     * @return ModelAndView to render index.html
     * @throws NotFoundException
     * @throws SQLException
     */
    public static ModelAndView renderFilteredProducts(Request req, Response res) throws NotFoundException, SQLException {
        LOGGER.info("renderFilteredProducts() method is called.");
        Map params = SessionEventController.setParams(req);

        if ( req.params(":categoryid") != null ) {
            LOGGER.info("Examine URL for categoryID to show the products filtered by category...");
            categoryId = Integer.parseInt(req.params(":categoryid"));
            params.put("products", productDB.getBy(productCategoryDB.find(categoryId)));
            req.session().attribute("currentUrl", "/category/" + req.params(":categoryid"));
        }
        else if ( req.params(":supplierid") != null ) {
            LOGGER.info("Examine URL for supplerID to show the products filtered by supplier...");
            supplierId = Integer.parseInt(req.params(":supplierid"));
            params.put("products", productDB.getBy(supplierDB.find(supplierId)));
            req.session().attribute("currentUrl", "/supplier/" + req.params(":supplierid"));
        }

        if ( req.params(":categoryid") != null ) {
            LOGGER.info("Examine URL for supplerID to show the name of category...");

            params.put("category", productCategoryDB.find(categoryId));
        }
        if ( req.params(":supplierid") != null ) {
            LOGGER.info("Examine URL for supplerID to show the name of supplier...");

            params.put("supplier", supplierDB.find(supplierId));
        }

        return new ModelAndView(params, "rendered_html/index");
    }

}