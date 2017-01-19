package com.codecool.shop.controller.rendered_page;


import com.codecool.shop.controller.event_controller.session_controller.SessionEventController;
import javassist.NotFoundException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.Map;

public class AboutUsControllerDB {

    /**
     * Render aboutus.html form. Action for display about us page.
     * Contain information about our wonderfully amazing group
     * @param req
     * @param res
     * @return ModelAndView render aboutus.html
     * @throws NotFoundException
     * @throws SQLException
     */
    public static ModelAndView renderAboutUs(Request req, Response res) throws NotFoundException, SQLException {
        Map params = SessionEventController.setParams(req);
        return new ModelAndView(params, "rendered_html/aboutus");
    }
}
