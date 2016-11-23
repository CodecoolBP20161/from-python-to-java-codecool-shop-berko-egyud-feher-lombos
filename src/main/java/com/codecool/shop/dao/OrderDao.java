package com.codecool.shop.dao;

import com.codecool.shop.model.Order;
import com.codecool.shop.model.Status;
import javassist.NotFoundException;

import java.util.List;


public interface OrderDao {
    void add(Order order);
    Order find(int id) throws NotFoundException;
    void remove(Order order);

    List<Order> getAll();
    List<Order> getBy(Status status) throws NotFoundException;

}
