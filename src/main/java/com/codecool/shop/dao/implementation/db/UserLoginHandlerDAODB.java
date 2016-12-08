package com.codecool.shop.dao.implementation.db;


import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class UserLoginHandlerDAODB extends AbstractDBHandler {

//    HashMap User{
//        "username":String,
//        "email":String,
//        "password":String,
//    }


    public void add(JSONObject user) throws SQLException {
        try {
            String salt = generateSalt();
            PreparedStatement stmt;
            stmt = getConnection().prepareStatement("INSERT INTO logintable (USERNAME, EMAIL, PASSWORD, SALT) VALUES (?, ?, ?, ?)");

            stmt.setString(1, user.getString("userName"));
            stmt.setString(2, user.getString("email"));
            stmt.setString(3, hash(user.getString("password") + salt));
            stmt.setString(4, salt);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean authenticate(String username, String inputPassword) throws SQLException, UnsupportedEncodingException, NoSuchAlgorithmException {
            String inputHash = hash(inputPassword + getSalt(username));
        return inputHash.equals(getPassword(username));
    }

    public int getId(String username) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM logintable WHERE username=?");
        stmt.setString(1, username);
        return stmt.executeQuery().getInt("ID");
    }

    private String getSalt(String username) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM logintable WHERE username=?");
        stmt.setString(1, username);
        return stmt.executeQuery().getString("SALT");
    }

    public Boolean checkIfUsernameExists(String userName) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM logintable WHERE username=?");
        stmt.setString(1, userName);
        return stmt.executeQuery().first();
    }

    public Boolean checkIfEmailExists(String email) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM logintable WHERE email=?");
        stmt.setString(1, email);
        return stmt.executeQuery().first();
    }

    private String hash(String string) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(string.getBytes("UTF-8"));
        return new BigInteger(1, crypt.digest()).toString(16);
    }

    private String generateSalt() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        return hash(new BigInteger(1, bytes).toString(20));
    }

    private static String getPassword(String username) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM logintable WHERE username=?");
        stmt.setString(1, username);
        return stmt.executeQuery().getString("PASSWORD");
    }
}


