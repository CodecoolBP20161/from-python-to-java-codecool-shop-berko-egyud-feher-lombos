package com.codecool.shop.controller;


import com.codecool.shop.controller.postal_fee_controller.PostalFeeCalculatorServiceController;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.process.CheckoutProcess;
import com.codecool.shop.model.process.PayProcess;
import javassist.NotFoundException;
import org.apache.http.client.HttpResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Map;

public class OrderControllerDB {

    private static final Logger logger = LoggerFactory.getLogger(OrderControllerDB.class);


    /**
     * Render cart.html form. Action for display cart content
     * @param req - Spark Request
     * @param res - Spark Response
     * @return ModelAndView to render cart.html
     * @throws NotFoundException
     * @throws SQLException
     */
    public static ModelAndView renderCartContent(Request req, Response res) throws NotFoundException, SQLException {
        Map params = Controller.setParams(req);
        return new ModelAndView(params, "rendered_html/cart");
    }


    /**
     * Render checkout.html form. Action for display checkout page & set order's status to CHECKED
     * @param req - Spark Request
     * @param res - Spark Response
     * @return ModelAndView to render checkout.html
     * @throws NotFoundException
     * @throws SQLException
     */
    public static ModelAndView renderCheckoutPage(Request req, Response res) throws NotFoundException, SQLException {
        Map params = Controller.setParams(req);
        Order order = req.session().attribute("Cart");
        order.setUserSessionId(req.session().id());

        // set order's status to "CHECKED"
        CheckoutProcess checkoutProcess = new CheckoutProcess();
        checkoutProcess.process(order);

        return new ModelAndView(params, "rendered_html/checkout");
    }


    /**
     * Render pay.html form. Action for display payment page & set order's status to PAID
     * @param req - Spark Request
     * @param res - Spark Response
     * @return ModelAndView to render pay.html
     * @throws NotFoundException
     * @throws SQLException
     */
    public static ModelAndView renderPaymentPage(Request req, Response res) throws NotFoundException, SQLException {
        Map params = Controller.setParams(req);


        return new ModelAndView(params, "rendered_html/pay");
    }


    /**
     * Render afterpay.html form. Action for display after payment page.
     * @param req - Spark Request
     * @param res - Spark Response
     * @return ModelAndView to render afterpay.html
     * @throws NotFoundException
     * @throws SQLException
     */
    public static ModelAndView renderAfterPaymentPage(Request req, Response res) throws NotFoundException, SQLException {
        Map params = Controller.setParams(req);
        Order order = req.session().attribute("Cart");

        System.out.println(order);

        // Set order's status to "PAID"
        PayProcess payProcess = new PayProcess();
        payProcess.process(order);

        params.put("navbarButtonsHREF", "removeAllCartContentFromSession");

        return new ModelAndView(params, "rendered_html/afterpay");
    }


    /**
     * Render shippinginformation.html to show the details of shopping.
     * @param req - Spark Request
     * @param res - Spark Response
     * @return ModelAndView to render shippinginformation.html
     * @throws NotFoundException
     * @throws SQLException
     * @author Moni
     * @version final
     */
    public static ModelAndView renderShippingInformationPage(Request req, Response res) throws NotFoundException, SQLException {
        Map params = Controller.setParams(req);
        Order order = req.session().attribute("Cart");


        try {
            if ((PostalFeeCalculatorServiceController.getPostalFee(req, order)).get(1).equals("Invalid parameters")){
                params.put("shippinginformationerror", "Couldn't calculated! Sorry! Please give a valid city to shipping data!");
            }
            params.put("shippinginformation", Float.parseFloat(PostalFeeCalculatorServiceController.getPostalFee(req, order).get(0).replace("$", "").trim()));
            logger.info("Getting  postal fee: " + Float.parseFloat(PostalFeeCalculatorServiceController.getPostalFee(req, order).get(0).replace("$", "").trim()));
        } catch (NumberFormatException e){
            logger.error("Getting error: " + e);
        } catch (NotFoundException e) {
            logger.error("Getting error: " + e);
        } catch (HttpResponseException e) {
            params.put("shippinginformationerror", "Sorry, the shipping isn't available yet, please contact us! ");
            logger.error("Getting error: " + e);
        } catch(IOException e) {
            logger.error("Getting error: " + e);
        } catch (URISyntaxException e ){
            logger.error("Getting error: " + e);
        }

        return new ModelAndView(params, "rendered_html/shippinginformation");
    }

}
