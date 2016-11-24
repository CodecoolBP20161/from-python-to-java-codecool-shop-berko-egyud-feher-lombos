package com.codecool.shop.dao;

import com.codecool.shop.model.Order;
import com.codecool.shop.model.Status;
import javassist.NotFoundException;

import java.sql.SQLException;
import java.util.List;


public interface OrderDao {
    void add(Order order);
    Order find(int id) throws NotFoundException;
    void remove(int id);

    List<Order> getAll() throws NotFoundException, SQLException;
    List<Order> getBy(Status status) throws NotFoundException;

}
