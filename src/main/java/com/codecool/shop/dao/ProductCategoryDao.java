package com.codecool.shop.dao;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import javassist.NotFoundException;

import java.util.List;

public interface ProductCategoryDao {

    void add(ProductCategory category);
    ProductCategory find(int id) throws NotFoundException;
    void remove(int id);

    List<ProductCategory> getAll();

}
