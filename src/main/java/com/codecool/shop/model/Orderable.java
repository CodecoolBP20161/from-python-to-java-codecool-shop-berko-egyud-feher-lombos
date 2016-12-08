package com.codecool.shop.model;


public interface Orderable {
    void add(Product item);
    void remove(Product item);
    boolean checkout();
    boolean pay();

}
