package com.codecool.shop.dao;


import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;
import javassist.NotFoundException;


import java.util.List;

public interface LineItemDao {
        void add(LineItem lineitem);
        void remove(int id);

        List<LineItem> getAll();
        List<LineItem> getBy(int orderId) throws NotFoundException;

}

