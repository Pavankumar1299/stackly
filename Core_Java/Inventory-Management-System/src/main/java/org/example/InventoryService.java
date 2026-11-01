package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.example.Category.*;

public class InventoryService {

    Map<Integer, Products> products = new HashMap<>();
    Map<Integer, Integer> soldProducts = new HashMap<>();

    Random random = new Random();

    public void loadProducts() {
        products.put(1221, new Products(1221, "ipad AIR 21", LAPTOP,68999.0, 4));
        products.put(1998, new Products(1998, "iphone 18", MOBILE,150000, 20));
        products.put(1785, new Products(1785, "Knife", HOME_APPLIANCE,299, 75));
        products.put(3562, new Products(3562, "Avita Liber A14", LAPTOP,48999, 10));
        products.put(6787, new Products(6787, "RealMe 9 pro plus ", MOBILE,29999, 21));
        products.put(1299, new Products(1299, "Washing Machine", HOME_APPLIANCE,21599, 5));
        products.put(5675, new Products(5675, "Drill Machine", ACCESSORY,7999, 13));
        products.put(9898, new Products(9898, "Asus new book 7", LAPTOP,52799, 3));
    }

    public void addProduct(String productName, Category category, double price, int stock) {

        int productId = random.nextInt(1000,10000) + 1;
        while (products.containsKey(productId)) {
            productId = random.nextInt(1000,10000) + 1;
        }

        Products product = new Products(productId, productName, category, price, stock);

        if (products.containsKey(product.getProductId())) {
            System.out.println("Product already exists!");
        } else {
            products.put(product.getProductId(), product);
            System.out.println("Added product successfully");
            System.out.println("ID: " + product.getProductId());
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

    public void totalBill() throws ProductException {

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

    }

    public void deleteProduct(int productId) throws ProductException {
        if (products.containsKey(productId)) {
            products.remove(productId);
        } else {
            throw new ProductException("Product not found!");
        }
    }
}
