package com.codecool.shop.model;


import java.util.HashSet;
import static com.codecool.shop.model.Status.*;


public class Order implements Orderable {
    private int id;
    private Status status;
    private double totalPrice;
    private int totalQuantity = 0;
    private static int idCount = 1;
    // contains all the LineItems in the shopping cart (order)
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
        return totalPrice;
    }

    public static int getIdCount() {
        return idCount;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setTotal(double total) {
        this.totalPrice = total;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public HashSet<LineItem> getItemsToBuy() {
        return itemsToBuy;
    }

    // creates a line item, and add it to itemsToBuy, and increments the quantity of the lineitem if it exists.
    public void add(Product item) {
        LineItem newItem = new LineItem(item);
        totalQuantity += 1;
        boolean contains = false;
        for (LineItem lineitem : itemsToBuy) {
            if (newItem.id == lineitem.id) {
                contains = true;
                lineitem.setQuantity(lineitem.getQuantity() + 1);
                break;
            }
        }
        if (!contains) {
            this.itemsToBuy.add(newItem);
        } this.setTotal(this.getTotal() + item.getDefaultPrice());
    }

    // creates a line item, and remove it from itemsToBuy, and decrease the quantity of the lineitem if it exists.
    public void remove(Product item) {
        LineItem newItem = new LineItem(item);
        totalQuantity -= 1;
        boolean contains = false;
        for (LineItem lineitem : itemsToBuy) {
            if (newItem.id == lineitem.id) {
                contains = true;
                lineitem.setQuantity(lineitem.getQuantity() - 1);
                break;
            }
        }
    }

    @Override
    public String toString() {
        return String.format("id: %1$d,\n" +
                        "status: %2$s, \n" +
                        "totalPrice: %3$f, \n" +
                        "totalQuantity: %4$f, \n" +
                        "itemsToBuy: %5$s\n",

                this.id,
                this.status,
                this.totalPrice,
                this.totalQuantity,
                this.itemsToBuy);


    }
}



