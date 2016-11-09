package com.codecool.shop.dao;

import com.codecool.shop.model.Order;
import com.codecool.shop.model.Status;

import java.util.List;

/**
 * Created by david on 11/9/16.
 */
public interface OrderDao {
    void add(Order order);
    Order find(int id);
    void remove(int id);

    List<Order> getAll();
    List<Order> getBy(Status status);

}
