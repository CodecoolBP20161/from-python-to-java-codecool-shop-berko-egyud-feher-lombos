package com.codecool.shop.model;

/**
 * Created by david on 11/8/16.
 */
public class LineItem extends Product {
    private int quantity;

    public LineItem(Product product) {
        this.setName(product.getName());
        this.setDefaultPrice(product.getDefaultPrice());
        this.setDefaultCurrency(product.getDefaultCurrency());
        this.setDescription(product.getDescription());
        this.setProductCategory(product.getProductCategory());
        this.setSupplier(product.getSupplier());
        this.quantity = 1;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
