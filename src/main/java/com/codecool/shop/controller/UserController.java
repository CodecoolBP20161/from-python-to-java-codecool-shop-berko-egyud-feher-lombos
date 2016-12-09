package com.codecool.shop.controller;


import com.codecool.shop.dao.implementation.db.UserLoginHandlerDAODB;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static UserLoginHandlerDAODB loginDB = new UserLoginHandlerDAODB();

    /**
     * renders the signin.html form, handles all the side cases
     * @param req - Spark Request
     * @param res - Spark Response
     * @return ModelAndView
     * @throws NotFoundException,SQLException - Exception handling at it's finest
     */
    public static ModelAndView renderSignIn(Request req, Response res) throws NotFoundException, SQLException {
        logger.info("RenderSignIn: Rendering signIn started...Checking user authentication.");
        if (req.session().attribute("authenticated").equals(false)) {
            logger.info("RenderSignIn: user is not authenticated.Rendering signIn form....");
            Map params = new HashMap<>();
            params.put("valid", true);
            return new ModelAndView(params, "product/signin");
        } else {
            logger.info("RenderSignIn: User is authenticated. Redirecting to index page.");
            res.redirect("/");
        }
        return null;
    }

    /**
     * renders the signup.html form, hadles all the side cases
     * @param req - Spark Request
     * @param res - Spark Response
     * @return ModelAndView
     * @throws NotFoundException,SQLException - Exception handling at it's finest
     */
    public static ModelAndView renderSignUp(Request req, Response res) throws NotFoundException, SQLException {
        logger.info("RenderSignUp: Rendering signUp started...Checking user authentication...");
        Map params = new HashMap<>();
        if (req.session().attribute("authenticated").equals(false)) {
            logger.info("RenderSignUp: User is not authenticated.Rendering signUp form...");

            return new ModelAndView(params, "product/signup");
        } else {
            logger.info("RenderSignUp: User is authenticated. Showing error message.");
            params.put("valid", false);
            params.put("message", "You are currently logged in. Please log out to register.");
            return new ModelAndView(params, "product/signup");
        }
    }
    /**
     * Gives the form parameters to check if the input is valid and sets the session for the user, handles all the side cases
     * @param req - Spark Request
     * @param res - Spark Response
     * @return ModelAndView
     * @throws NotFoundException,SQLException,UnsupportedEncodingException,NoSuchAlgorithmException - Exception handling at it's finest
     */
    public static ModelAndView login(Request req, Response res) throws NotFoundException, SQLException, UnsupportedEncodingException, NoSuchAlgorithmException {
        logger.info("Login: Login attempt started. Checking authentication.");
        if(req.session().attribute("authenticated").equals(true)) {
            logger.info("Login: User is already logged in. Redirecting to index page.");
            res.redirect("/");
        } else {
            logger.info("Login: User is not authenticated. Checking if inputs are valid...");

            String inputPassword = req.queryParams("password");
            String username = req.queryParams("username");
            if (loginDB.authenticate(username, inputPassword)) {
                logger.info("Login: Inputs are valid. Logging in...");
                req.session().attribute("authenticated", true);
                req.session().attribute("username", username);
                res.redirect("/");
                return null;
            } else {
                logger.info("Login: Invalid inputs. Rendering form with error message.");
                Map params = new HashMap<>();
                params.put("valid", false);
                params.put("message", "Incorrect username or password.");
                return new ModelAndView(params, "product/signin");
            }
        }
        return null;
    }
    /**
     * Saves the new user's parameters and redirects to the index page, handles all side cases
     * @param req - Spark Request
     * @param res - Spark Response
     * @return ModelAndView
     * @throws NotFoundException,SQLException - Exception handling at it's finest
     */
    public static ModelAndView createUser(Request req, Response res) throws NotFoundException, SQLException {
        logger.info("CreateUser: User creation is called. Checking authentication.");
        Map params = new HashMap();
        if (req.session().attribute("authenticated").equals(true)) {
            logger.info("CreateUser: User is authenticated. Rendering form with error message.");
            params.put("valid", false);
            params.put("message", "You are logged in. Please log out to continue.");
            return new ModelAndView(params, "product/signup");
        } else {
            logger.info("CreateUser: User is not authenticated. Checking if email or username is already in use.");
            if (!(loginDB.checkIfEmailExists(req.queryParams("email")) || loginDB.checkIfUsernameExists(req.queryParams("username")))) {
                logger.info("CreateUser: email and username is not in use. Saving new user to database.");
                loginDB.add(req.queryParams("username"), req.queryParams("password"), req.queryParams("email"));
                logger.info("CreateUser: New User saved. Redirecting to index page.");
                res.redirect("/");
            }else {
                logger.info("CreateUser: email or username is in use. Rendering form with error message.");
                params.put("valid", false);
                params.put("message", "Email or username already in use.");
                return new ModelAndView(params, "product/signup");
            }
        }
        return null;
    }
    /**
     * Resets the session for the user and redirects to the index page
     * @param req - Spark Request
     * @param res - Spark Response
     * @return ModelAndView
     */
    public static ModelAndView logOut(Request req, Response res){
        logger.info("LogOut: Logout has started. Setting parameters to null.");
        req.session().attribute("authenticated", false);
        req.session().attribute("username", null);
        res.redirect("/");
        return  null;
    }

}


