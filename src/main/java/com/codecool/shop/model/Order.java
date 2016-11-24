package com.codecool.shop.model;


import java.util.HashSet;
import static com.codecool.shop.model.Status.*;


public class Order implements Orderable {
    private int id;
    private Status status;
    private double totalPrice = 0;
    private int totalQuantity = 0;
    // contains all the LineItems in the shopping cart (order)
    private HashSet<LineItem> itemsToBuy = new HashSet<>();

    public void setId(int id) {
        this.id = id;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public void setItemsToBuy(HashSet<LineItem> itemsToBuy) {
        this.itemsToBuy = itemsToBuy;
    }


    public Order() {
        this.status = CART;
        this.setTotalQuantity(0);
    }

    public Order(Status status, int id) {
        this.setId(id);
        this.setStatus(status);
        this.setTotalPrice(totalPrice);
        this.setTotalQuantity(totalQuantity);
        this.setItemsToBuy(itemsToBuy);
    }

    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }


    public void setStatus(Status status) {
        this.status = status;
    }

    public void setTotalPrice(double total) {
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
        LineItem newItem = new LineItem(item, this.getId());
        totalQuantity += 1;
        boolean contains = false;
        for (LineItem lineitem : itemsToBuy)
            if (newItem.getProductId() == lineitem.getProductId()) {
                contains = true;
                lineitem.setQuantity(lineitem.getQuantity() + 1);
                break;
            }
        if (!contains) {
            this.itemsToBuy.add(newItem);
        } this.setTotalPrice(this.getTotalPrice() + item.getDefaultPrice());
    }

    // creates a line item, and remove it from itemsToBuy, and decrease the quantity of the lineitem if it exists.
    public void remove(Product item) {
        LineItem newItem = new LineItem(item, this.getId());
        totalQuantity -= 1;
        for (LineItem lineitem : itemsToBuy)
            if (newItem.id == lineitem.id) {
                lineitem.setQuantity(lineitem.getQuantity() - 1);
                itemsToBuy.remove(newItem);
                if (lineitem.getQuantity() == 0) {
                    itemsToBuy.remove(lineitem);
                }
                break;
            }
    }

    @Override
    public String toString() {
        return String.format("id: %1$d, " +
                        "status: %2$s, " +
                        "totalPrice: %3$f, " +
                        "totalQuantity: %4$d, " +
                        "itemsToBuy: %5$s",

                this.id,
                this.status,
                this.totalPrice,
                this.totalQuantity,
                this.itemsToBuy);


    }
}



