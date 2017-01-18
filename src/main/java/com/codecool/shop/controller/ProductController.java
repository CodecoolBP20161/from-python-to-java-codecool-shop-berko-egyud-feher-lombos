package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.mem.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.mem.ProductDaoMem;
import com.codecool.shop.dao.implementation.mem.SupplierDaoMem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Orderable;
import com.codecool.shop.model.ProductCategory;
import javassist.NotFoundException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class ProductController {

    private static ProductDao productDataStore = ProductDaoMem.getInstance();
    private static ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
    private static SupplierDao productSupplierDataStore = SupplierDaoMem.getInstance();

    static Integer categoryId = 1;
    static Integer supplierId = 1;


    /**
     * Handle the content of the params Hashmap
     * @param req - Spark Request
     * @return the filtered or non-filtered products in a HashMap
     * @throws NotFoundException
     * @throws SQLException
     */
    public static Map setParams(Request req) throws NotFoundException, SQLException {
        Map params = new HashMap<>();
        params.put("category", new ProductCategory("All Products", "All Products", "All Products"));
        params.put("categories", productCategoryDataStore.getAll());
        params.put("products", productDataStore.getAll());
        params.put("suppliers", productSupplierDataStore.getAll());
        req.session().attribute("currentUrl", "/");
        if ( req.params(":categoryid") != null ) {
            categoryId = Integer.parseInt(req.params(":categoryid"));
            params.put("products", productDataStore.getBy(productCategoryDataStore.find(categoryId)));
            req.session().attribute("currentUrl", "/category/" + req.params(":categoryid"));

        }
        else if ( req.params(":supplierid") != null ) {
            supplierId = Integer.parseInt(req.params(":supplierid"));
            params.put("products", productDataStore.getBy(productSupplierDataStore.find(supplierId)));
            req.session().attribute("currentUrl", "/supplier/" + req.params(":supplierid"));
        }
        else if ( req.url().equals("http://localhost:8888/cartcontent")) {
            req.session().attribute("currentUrl", "/cartcontent");
        }
        Order cart = req.session().attribute("Cart");
        params.put("cart", cart);
        return params;
    }


    /**
     * Action for display all or filtered products.
     * Render the index.html form.
     * @param req - Spark Request
     * @param res - Spark Response
     * @return ModelAndView to render index.html
     * @throws NotFoundException
     * @throws SQLException
     */
    public static ModelAndView renderProducts(Request req, Response res) throws NotFoundException, SQLException {
        Map params = setParams(req);
        if ( req.params(":categoryid") != null ) {
            params.put("category", productCategoryDataStore.find(categoryId));
        }
        if ( req.params(":supplierid") != null ) {
            params.put("supplier", productSupplierDataStore.find(supplierId));
        }
        return new ModelAndView(params, "rendered_html/index");
    }


    /**
     * Action for display cart content.
     * Render the cart.html form.
     * @param req - Spark Request
     * @param res - Spark Response
     * @return ModelAndView to render cart.html
     * @throws NotFoundException
     * @throws SQLException
     */
    public static ModelAndView renderCartContent(Request req, Response res) throws NotFoundException, SQLException {
        Map params = setParams(req);
        return new ModelAndView(params, "rendered_html/cart");
    }


    /**
     * Handle the content of the session and set the variables of Order object.
     * Add the item with the given id to the cart.
     * @param req - Spark Request
     * @param res - Spark Response
     * @return null
     * @throws NotFoundException
     */
    public static String addToCart(Request req, Response res) throws NotFoundException {
        int id = Integer.parseInt(req.params(":id"));
        Orderable cart;

        if (req.session().attribute("Cart") == null) {
            cart = new Order();
        } else {
            cart = req.session().attribute("Cart");
        }

        cart.add(productDataStore.find(id));
        req.session().attribute("Cart", cart);

        res.redirect(req.session().attribute("currentUrl"));
        return null;
    }


    /**
     * Handle the content of the session and set the variables of Order object.
     * Remove the item with the given id from the cart.
     * @param req - Spark Request
     * @param res - Spark Response
     * @return null
     * @throws NotFoundException
     */
    public static String removeFromCart(Request req, Response res) throws NotFoundException {
        int id = Integer.parseInt(req.params(":id"));
        Orderable cart;

        if (req.session().attribute("Cart") == null) {
            cart = new Order();
        } else {
            cart = req.session().attribute("Cart");
        }
        cart.remove(productDataStore.find(id));
        req.session().attribute("Cart", cart);
        res.redirect(req.session().attribute("currentUrl"));
        return null;
    }
}