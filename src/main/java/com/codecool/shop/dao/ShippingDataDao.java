package com.codecool.shop.dao;


import com.codecool.shop.model.Order;

import java.util.ArrayList;

public interface ShippingDataDao {

    void add(ArrayList<String> shippingDataList, Order order);
//    void remove(int id);
//    void find(int id);

}
