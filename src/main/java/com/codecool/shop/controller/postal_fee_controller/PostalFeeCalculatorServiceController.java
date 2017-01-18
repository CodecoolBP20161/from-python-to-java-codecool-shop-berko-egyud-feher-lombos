package com.codecool.shop.controller.postal_fee_controller;

import com.codecool.shop.dao.implementation.db.ShippingDataDB;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Orderable;
import javassist.NotFoundException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.utils.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;


public class PostalFeeCalculatorServiceController {

    private static final Logger logger = LoggerFactory.getLogger(PostalFeeCalculatorServiceController.class);
    private static final String SERVICE_URL = "http://localhost:60001";
    private static final String TARGET_PARAM_KEY = "target";
    private static final String WEBSHOP_PARAM_KEY= "webshop";

    /**
     * <h1>Get two city and build an API URL to get the postal fee from the postal_fee_calculator_microservice.
     * One of the city is the userCity, what we get from the database and the other is the webshopCity, that is permanent.
     * </h1>
     * @param request
     * @param order
     * @return cost from the JSON returned by microservice
     * @throws IOException
     * @throws URISyntaxException
     * @throws NotFoundException
     * @author Vivi and Moni
     * @version final
     */
    public static ArrayList<String> getPostalFee(spark.Request request, Order order) throws IOException, URISyntaxException, NotFoundException {
        logger.info("Getting Postal Fee...");

        String webshopCity = "Budapest";

        ShippingDataDB shippingDataDB = ShippingDataDB.getInstance();
        String userCity = shippingDataDB.find(order.getId()).get(3);
        URIBuilder builder = new URIBuilder(SERVICE_URL + "/api");
        builder.addParameter(TARGET_PARAM_KEY, userCity);
        builder.addParameter(WEBSHOP_PARAM_KEY, webshopCity);
        logger.info("Getting URL " + builder.build());

        String cost = execute(builder.build());
        JSONObject json = new JSONObject(cost);

        logger.info("Getting cost from JSON " + json);

        ArrayList<String> postalFeeAsJson = new ArrayList<>();
        postalFeeAsJson.add(0, json.getString("cost"));
        postalFeeAsJson.add(1, json.getString("status"));
        logger.info("Getting postalFeeAsJson " + postalFeeAsJson);

        return postalFeeAsJson;
    }

    /**
     *<h1>Execute get request with the built URL and return the response.</h1>
     * @param uri
     * @return the response
     * @throws IOException
     */
    private static String execute(URI uri) throws IOException {
        return Request.Get(uri)
                .execute()
                .returnContent()
                .asString();
    }
}
