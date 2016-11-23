package com.codecool.shop.dao;

import com.codecool.shop.model.Supplier;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import javassist.NotFoundException;

import java.util.List;

public interface ProductDao {

    void add(Product product);
    Product find(int id) throws NotFoundException;
    void remove(Product product);

    List<Product> getAll();
    List<Product> getBy(Supplier supplier) throws NotFoundException;
    List<Product> getBy(ProductCategory productCategory) throws NotFoundException;

}
