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

    private static final Logger LOGGER = LoggerFactory.getLogger(PostalTimeServiceController .class);
    private static final String SERVICE_URL = "http://0.0.0.0:60003/api/timecalculator/";
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
        LOGGER.debug("Getting postal time with getPostalTime() method.");

        LOGGER.debug("Generating URL...");
        ShippingDataDB shippingDataDB = ShippingDataDB.getInstance();
        String userCity = shippingDataDB.find(order.getId()).get(3);
        URIBuilder builder = new URIBuilder(SERVICE_URL + ORIGIN_CITY + "/" + userCity);
        LOGGER.info("URL: " + builder.build());

        String time = execute(builder.build());
        JSONObject jsonOfTime = new JSONObject(time);

        if (jsonOfTime.getString("status").equals("NOT_FOUND ERROR: Place doesn't exist!")) throw new NotFoundException("City not found. :(");
        if (jsonOfTime.getString("status").equals("ZERO_RESULTS ERROR: Oversea location!")) throw new NotFoundException("Location is overseas. :(");

        return String.valueOf((int) Math.ceil(jsonOfTime.getDouble("time") / 86400000));
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
