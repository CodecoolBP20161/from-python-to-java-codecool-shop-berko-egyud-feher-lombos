package com.codecool.shop.model;

import java.util.Currency;

public class Product extends BaseModel {

    float defaultPrice;
    Currency defaultCurrency;
    ProductCategory productCategory;
    protected Supplier supplier;

    public Product(int id, String name,
                   float defaultPrice,
                   String currencyString,
                   String description,
                   ProductCategory Category,
                   Supplier supplier) {

        super(name, description);
        this.setId(id);
        this.setPrice(defaultPrice, currencyString);
        this.setSupplier(supplier);
        this.setProductCategory(Category);
    }
    public Product( String name,
                    float defaultPrice,
                    String currencyString,
                    String description,
                    ProductCategory Category,
                    Supplier supplier) {

        super(name, description);
        this.setPrice(defaultPrice, currencyString);
        this.setSupplier(supplier);
        this.setProductCategory(Category);
    }

    public Product() {
    }

    public float getDefaultPrice() {
        return defaultPrice;
    }

    void setDefaultPrice(float defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public Currency getDefaultCurrency() {
        return defaultCurrency;
    }

    void setDefaultCurrency(Currency defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    void setPrice(float price, String currency) {
        this.defaultPrice = price;
        this.defaultCurrency = Currency.getInstance(currency);
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
        this.productCategory.addProduct(this);
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
        this.supplier.addProduct(this);
    }

    @Override
    public String toString() {
        return String.format("id: %1$d, name: %2$s, defaultPrice: %3$f, defaultCurrency: %4$s, productCategory: %5$s, supplier: %6$s",
                this.id,
                this.name,
                this.defaultPrice,
                this.defaultCurrency.toString(),
                this.productCategory.getName(),
                this.supplier.getName());
    }
}
