package org.example;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        InventoryService inventoryService = new InventoryService();

        boolean running = true;

        do {
            System.out.println("\n===== INVENTORY MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Product");
            System.out.println("2. Search Product");
            System.out.println("3. Update Product Price");
            System.out.println("4. Add Stock");
            System.out.println("5. Sell Product");
            System.out.println("6. Show All Products");
            System.out.println("7. Show Low Stock Products");
            System.out.println("8. Calculate Bill");
            System.out.println("9. Delete Product");
            System.out.println("10. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {

                case 1 -> {
                    System.out.print("Enter Product ID: ");
                    int productId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter Product Name: ");
                    String productName = scanner.nextLine();

                    System.out.print("Enter Category (LAPTOP/MOBILE/ACCESSORY/HOME_APPLIANCE): ");
                    Category category = Category.valueOf(scanner.nextLine().toUpperCase());

                    System.out.print("Enter Product Price: ");
                    double price = scanner.nextDouble();

                    System.out.print("Enter Stock: ");
                    int stock = scanner.nextInt();

                    Products product = new Products(
                            productId,
                            productName,
                            category,
                            price,
                            stock
                    );

                    inventoryService.addProduct(product);
                }

                case 2 -> {
                    System.out.print("Enter Product ID: ");
                    int productId = scanner.nextInt();

                    try {
                        Products product = inventoryService.searchByProductId(productId);
                        System.out.println(product);
                    } catch (ProductException e) {
                        System.out.println(e.getMessage());
                    }
                }

                case 3 -> {
                    System.out.print("Enter Product ID: ");
                    int productId = scanner.nextInt();

                    System.out.print("Enter New Price: ");
                    double newPrice = scanner.nextDouble();

                    try {
                        inventoryService.updateProductPrice(productId, newPrice);
                        System.out.println("Price updated successfully!");
                    } catch (ProductException e) {
                        System.out.println(e.getMessage());
                    }
                }

                case 4 -> {
                    System.out.print("Enter Product ID: ");
                    int productId = scanner.nextInt();

                    System.out.print("Enter Quantity to Add: ");
                    int quantity = scanner.nextInt();

                    try {
                        inventoryService.addStock(productId, quantity);
                        System.out.println("Stock added successfully!");
                    } catch (ProductException e) {
                        System.out.println(e.getMessage());
                    }
                }

                case 5 -> {
                    System.out.print("Enter Product ID: ");
                    int productId = scanner.nextInt();

                    System.out.print("Enter Quantity to Sell: ");
                    int quantity = scanner.nextInt();

                    try {
                        inventoryService.sellProduct(productId, quantity);
                    } catch (ProductException e) {
                        System.out.println(e.getMessage());
                    }
                }

                case 6 -> inventoryService.showAllProducts();

                case 7 -> {
                    try {
                        inventoryService.stockBelowFive();
                    } catch (ProductException e) {
                        System.out.println(e.getMessage());
                    }
                }

                case 8 -> {
                    try {
                        inventoryService.totalBill();
                    } catch (ProductException e) {
                        System.out.println(e.getMessage());
                    }
                }

                case 9 -> {
                    System.out.print("Enter Product ID: ");
                    int productId = scanner.nextInt();

                    try {
                        inventoryService.deleteProduct(productId);
                        System.out.println("Product deleted successfully!");
                    } catch (ProductException e) {
                        System.out.println(e.getMessage());
                    }
                }

                case 10 -> {
                    System.out.println("Exiting Inventory Management System...");
                    running = false;
                }

                default -> System.out.println("Invalid choice!");
            }

        } while (running);

        scanner.close();
    }
}