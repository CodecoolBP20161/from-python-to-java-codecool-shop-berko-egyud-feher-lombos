package com.codecool.shop.dao;

import com.codecool.shop.model.Product;

public interface Orderable {
    void add(Product item);
}
