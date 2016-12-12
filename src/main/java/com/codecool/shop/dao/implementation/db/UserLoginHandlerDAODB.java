package com.codecool.shop.dao.implementation.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLoginHandlerDAODB extends AbstractDBHandler {
    private static final Logger logger = LoggerFactory.getLogger(UserLoginHandlerDAODB.class);

    /**
     * adds a new user entry to the database
     * @param username - new username to get saved
     * @param password - new password to get saved
     * @param email - new email to get saved
     * @throws SQLException - Exception at it's finest
     */
    public void add(String username, String password, String email) throws SQLException {
        logger.info("add method is called.");
        try {
            String salt = generateSalt();
            PreparedStatement stmt;
            stmt = getConnection().prepareStatement("INSERT INTO logintable (USERNAME, EMAIL, PASSWORD, SALT) VALUES (?, ?, ?, ?)");

            stmt.setString(1,username);
            stmt.setString(2, email);
            stmt.setString(3, hash(password + salt));
            stmt.setString(4, salt);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error occurred during order find(looked) in database: {}", e);
        }
    }

    /**
     * checks if the input params are the same as the corresponding database params
     * @param username - new username to get saved
     * @param inputPassword - new password to get saved
     * @return boolean
     * @throws SQLException,UnsupportedEncodingException,NoSuchAlgorithmException - Exception at it's finest
     */
    public boolean authenticate(String username, String inputPassword) throws SQLException, UnsupportedEncodingException, NoSuchAlgorithmException {
        logger.info("authenticate method is called.");

        String inputHash = hash(inputPassword + getSalt(username));
        return inputHash.equals(getPassword(username));
    }

    /**
     * returns the ID which refers to the username param in the database
     * @param username - new username to search in the database
     * @return int - id
     * @throws SQLException - Exceptions at it's finest
     */
    public int getId(String username) throws SQLException {
        logger.info("getId method is called.");

        PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM logintable WHERE username=?");
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()) {
            return rs.getInt("ID");
        }
        return 0;
    }

    /**
     * returns the salt which refers to the username param in the database
     * @param username - username to search in the database
     * @return string - salt
     * @throws SQLException - Exceptions at it's finest
     */
    private String getSalt(String username) throws SQLException {
        logger.info("getSalt method is called.");

        PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM logintable WHERE username=?");
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()) {
            return rs.getString("SALT");
        }
        return null;
    }

    /**
     * checks if a username is already used by another user in the database
     * @param username - username to search in the database
     ** @return boolean
     * @throws SQLException - Exceptions at it's finest
     */
    public Boolean checkIfUsernameExists(String username) throws SQLException {
        logger.info("checkIfUsernameExists method is called.");

        PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM logintable WHERE username=?");
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        return rs.next();
    }

    /**
     * checks if a email is already used by another user in the database
     * @param email - username to search in the database
     * @return boolean
     * @throws SQLException - Exceptions at it's finest
     */
    public Boolean checkIfEmailExists(String email) throws SQLException {
        logger.info("checkIfEmailExists method is called.");

        PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM logintable WHERE email=?");
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        return rs.next();

    }

    /**
     * generates a hash from a the string param by SHA-1 encryption
     * @param string - username to search in the database
     * @return string - encrypted string
     * @throws NoSuchAlgorithmException, UnsupportedEncodingException - Exceptions at it's finest
     */
    private String hash(String string) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        logger.info("hash method is called.");

        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(string.getBytes("UTF-8"));
        return new BigInteger(1, crypt.digest()).toString(16);
    }

    /**
     * generates a random 20 byte long salt, and creates a hash from it
     * @return string - generated salt as a string
     * @throws UnsupportedEncodingException,NoSuchAlgorithmException - Exceptions at it's finest
     */
    private String generateSalt() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        logger.info("generateSalt method is called.");

        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        return hash(new BigInteger(1, bytes).toString(20));
    }

    /**
     * gets the password which belongs to the username param in the database
     * @param username - username to search in the database
     * @return string - password from the database
     * @throws SQLException - Exceptions at it's finest
     */
    private String getPassword(String username) throws SQLException {
        logger.info("getPassword method is called.");

        PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM logintable WHERE username=?");
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()) {
            return rs.getString("PASSWORD");
        }
        return null;
    }
}


