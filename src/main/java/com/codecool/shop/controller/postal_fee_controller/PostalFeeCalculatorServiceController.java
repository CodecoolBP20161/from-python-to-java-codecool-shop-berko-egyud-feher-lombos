package com.codecool.shop.controller.postal_fee_controller;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.utils.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by lombocska on 2017. 01. 16..
 */
public class PostalFeeCalculatorServiceController {

    private static final Logger logger = LoggerFactory.getLogger(PostalFeeCalculatorServiceController .class);
    private static final String SERVICE_URL = "http://localhost:60000";
    private static final String TARGET_PARAM_KEY = "target";
    private static final String WEBSHOP_PARAM_KEY="webshop";


    public static String getPostalFee(spark.Request request) throws IOException, URISyntaxException {
        logger.info("Getting Postal Fee...");

        // TODO: get the userCity from shippingdata' table instead of this userCity
        String userCity = "Budapest";
        String webshopCity = "Polgardi";
        URIBuilder builder = new URIBuilder(SERVICE_URL + "/api");
        builder.addParameter(TARGET_PARAM_KEY, userCity);
        builder.addParameter(WEBSHOP_PARAM_KEY, webshopCity);
        logger.warn("PROBA" + builder.build());
        return execute(builder.build());
    }

    private static String execute(URI uri) throws IOException {
        return Request.Get(uri)
                .execute()
                .returnContent()
                .asString();
    }
}
