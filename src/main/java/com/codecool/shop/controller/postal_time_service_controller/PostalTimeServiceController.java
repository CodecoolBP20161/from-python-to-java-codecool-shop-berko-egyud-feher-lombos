package com.codecool.shop.controller.postal_time_service_controller;

import com.codecool.shop.dao.implementation.db.ShippingDataDB;
import com.codecool.shop.model.Order;
import javassist.NotFoundException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class PostalTimeServiceController {

    private static final Logger logger = LoggerFactory.getLogger(PostalTimeServiceController .class);
    private static final String SERVICE_URL = " http://0.0.0.0:60003/api/timecalculator/";
    private static final String TARGET_PARAM_KEY = "target";
    private static final String ORIGIN_PARAM_KEY = "origin";
    public  static final String ORIGIN_CITY = "Budapest";

    /**
     * <h1>Sends the user's city to the microservice, and pr
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
    public static String getPostalTime(spark.Request request, Order order) throws IOException, URISyntaxException, NotFoundException {
        logger.info("Getting postal time");

        logger.info("Generating URL...");
        ShippingDataDB shippingDataDB = ShippingDataDB.getInstance();
        String userCity = shippingDataDB.find(order.getId()).get(3);
        URIBuilder builder = new URIBuilder(SERVICE_URL + ORIGIN_CITY + "/" + userCity);
        logger.info("URL: " + builder.build());

        String time = execute(builder.build());
        JSONObject json = new JSONObject(time);
        logger.info("Getting cost from JSON" + json);

        return Math.floor((json.getInt("time")) / 86400000) + "";
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
