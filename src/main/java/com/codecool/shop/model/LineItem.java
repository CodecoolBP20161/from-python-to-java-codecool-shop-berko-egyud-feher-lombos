package com.codecool.shop.model;


public class LineItem extends Product {
    private int quantity;
    private int productId;
    private int orderId;


    // constructor that takes a rendered_html as argument, and construct a line item for display
    public LineItem(Product product, int orderId) {
        this.orderId = orderId;
        this.setProductId(product.getId());
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

    @Override
    public String toString() {
        return String.format("id: %1$d, " +
                        "name: %2$s, " +
                        "defaultPrice: %3$f, " +
                        "defaultCurrency: %4$s, " +
                        "productCategory: %5$s, " +
                        "supplier: %6$s, " +
                        "quantity: %7$d",
                this.id,
                this.name,
                this.defaultPrice,
                this.defaultCurrency.toString(),
                this.productCategory.getName(),
                this.supplier.getName(),
                this.getQuantity());
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
