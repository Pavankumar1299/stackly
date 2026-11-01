package org.example;

public class Bill {

    private int productId;
    private int quantity;
    private double totalAmount;

    public Bill(int productId, int quantity, double totalAmount) {
        this.productId = productId;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "productId=" + productId +
                ", quantity=" + quantity +
                ", totalAmount=" + totalAmount +
                '}';
    }
}