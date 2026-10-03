package org.example;

import java.util.HashMap;
import java.util.Map;

public class InventoryService {

    Map<Integer, Products> products = new HashMap<>();
    Map<Integer, Integer> soldProducts = new HashMap<>();

    public void addProduct(Products product){
        if (products.containsKey(product.getProductId())) {
            System.out.println("Product already exists!");
        } else {
            products.put(product.getProductId(), product);
            System.out.println("Added product " + product.getProductId());
        }
    }

    public Products searchByProductId(int productId) throws ProductException {
        if (products.containsKey(productId)) {
            System.out.println("Product found!");
            return products.get(productId);
        } else {
            throw new ProductException("Product not found!");
        }
    }

    public void updateProductPrice(int productId, double newPrice) throws ProductException {
        if (products.containsKey(productId)) {
            products.get(productId).setProductPrice(newPrice);
        } else {
            throw new ProductException("Product not found!");
        }
    }

    public void addStock(int productId, int quantity) throws ProductException {
        if (products.containsKey(productId)) {
            products.get(productId).setStock(products.get(productId).getStock() + quantity);
        } else {
            throw new ProductException("Product not found!");
        }
    }

    public void sellProduct(int productId, int quantity) throws ProductException {

        if (products.containsKey(productId)) {

            Products product = products.get(productId);

            if (product.getStock() == 0) {
                throw new ProductException("Product is out of stock!");
            }

            if (quantity > product.getStock()) {
                throw new ProductException("Insufficient stock!");
            }

            product.setStock(product.getStock() - quantity);

            soldProducts.put(
                    productId,
                    soldProducts.getOrDefault(productId, 0) + quantity
            );

            System.out.println("Product sold successfully!");

        } else {
            throw new ProductException("Product not found!");
        }
    }

    public void showAllProducts() {
        for (Products p : products.values()) {
            System.out.println(p);
        }
        if (products.isEmpty()) {
            System.out.println("Product not found!");
        };
    }

    public void stockBelowFive() throws ProductException {
        for (Products p : products.values()) {
            if (p.getStock() < 5) {
                System.out.println(p);
            }
        }
        if (products.isEmpty()) {
            System.out.println("Product not found!");
        }
    }

    public double totalBill() throws ProductException {

        double total = 0;

        System.out.println("\n========== BILL ==========");

        for (Map.Entry<Integer, Integer> entry : soldProducts.entrySet()) {

            int productId = entry.getKey();
            int quantity = entry.getValue();

            Products product = products.get(productId);

            if (product == null) {
                throw new ProductException("Product not found!");
            }

            double itemTotal = product.getProductPrice() * quantity;
            total += itemTotal;

            System.out.printf(
                    "%-12s %.2f × %d = %.2f%n",
                    product.getProductName(),
                    product.getProductPrice(),
                    quantity,
                    itemTotal
            );
        }

        System.out.println("--------------------------------");
        System.out.printf("%-26s %.2f%n", "Total", total);
        System.out.println("============================");

        soldProducts.clear();

        return total;
    }

    public void deleteProduct(int productId) throws ProductException {
        if (products.containsKey(productId)) {
            products.remove(productId);
        } else {
            throw new ProductException("Product not found!");
        }
    }
}
