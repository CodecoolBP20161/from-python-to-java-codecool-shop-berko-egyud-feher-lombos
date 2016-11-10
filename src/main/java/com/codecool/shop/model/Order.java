package com.codecool.shop.model;

import java.util.HashSet;

import static com.codecool.shop.model.Status.*;

/**
 * Created by david on 11/8/16.
 */
public class Order implements Orderable {
    private int id;
    private int totalQuantity = 0;
    private Status status;
    private double total;
    private static int idCount = 1;
    private HashSet<LineItem> itemsToBuy = new HashSet<>();


    {
        this.id = idCount++;
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

    public void setTotal(double total) {
        this.total = total;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }


    public HashSet<LineItem> getItemsToBuy() {
        return itemsToBuy;
    }

    public void add(Product item) {
        LineItem newItem = new LineItem(item);
        totalQuantity += 1;
        boolean contains = false;
        for (LineItem k : itemsToBuy) {
            if (newItem.id == k.id) {
                contains = true;
                k.setQuantity(k.getQuantity() + 1);
                break;
            }
        }
        if (!contains) {
            this.itemsToBuy.add(newItem);
        } this.setTotal(this.getTotal() + item.getDefaultPrice());
    }

    @Override
    public String toString() {
        return String.format("id: %1$d,\n" +
                        "status: %2$s, \n" +
                        "total: %3$f, \n" +
                        "itemsToBuy: %4$s\n",
                this.id,
                this.status,
                this.total,
                this.itemsToBuy);


    }
}



