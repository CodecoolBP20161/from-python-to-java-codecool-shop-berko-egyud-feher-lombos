package com.codecool.shop.controller.postal_fee_controller;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by lombocska on 2017. 01. 16..
 */
public class PostalFeeCalculatorServiceController {

    private static final Logger logger = LoggerFactory.getLogger(PostalFeeCalculatorController .class);
    private static final String SERVICE_URL = "http://localhost:60000/";

    public static String getPostalFee(){

        return null;
    }

    private static String execute(String url) throws IOException, URISyntaxException {
        URI uri = new URIBuilder(SERVICE_URL + url).build();
        return Request.Get(uri).execute().returnContent().asString();
    }
}
