package com.codecool.shop.dao;

import com.codecool.shop.model.Supplier;
import javassist.NotFoundException;

import java.util.List;

public interface SupplierDao {

    void add(Supplier category);
    Supplier find(int id) throws NotFoundException;
    void remove(int id);

    List<Supplier> getAll() throws NotFoundException;
}
