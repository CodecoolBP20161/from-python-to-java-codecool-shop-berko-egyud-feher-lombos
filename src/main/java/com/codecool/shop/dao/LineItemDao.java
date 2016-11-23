package com.codecool.shop.dao;


import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;
import javassist.NotFoundException;


import java.util.List;

public interface LineItemDao {
        void add(LineItem lineitem);
        LineItem find(int id);
        void remove(LineItem lineItem);

        List<LineItem> getAll();
        List<LineItem> getBy(int orderId) throws NotFoundException;

}

