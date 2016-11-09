package com.codecool.shop.model;

import com.codecool.shop.dao.OrderDao;

import java.util.ArrayList;
import java.util.HashSet;

import static com.codecool.shop.model.Status.*;

/**
 * Created by david on 11/8/16.
 */
public class Order implements Orderable {
    private int id;
    private Status status;
    private double total;
    private static int idCount = 1;
    private HashSet<LineItem> itemsToBuy = new HashSet<>();

    {
        this.id = idCount++;
        System.out.println(this.id);
    }

    public Order() {
        this.status = CART;
    }

    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public double getTotal() {
        return total;
    }

    public static int getIdCount() {
        return idCount;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public HashSet<LineItem> getItemsToBuy() {
        return itemsToBuy;
    }

    public void add(Product item){
        LineItem newItem = new LineItem(item);
        if (this.itemsToBuy.contains(newItem)) {
            newItem.setQuantity(newItem.getQuantity() + 1);
        } else {
            this.itemsToBuy.add(newItem);
        }
    }
}



