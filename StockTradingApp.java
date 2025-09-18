package com.saravanan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class StockTradingApp {

    // --- Stock Class ---
    static class Stock {
        private String symbol;
        private String name;
        private double price;

        public Stock(String symbol, String name, double price) {
            this.symbol = symbol;
            this.name = name;
            this.price = price;
        }

        public String getSymbol() {
            return symbol;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double newPrice) {
            this.price = newPrice;
        }

        public String toString() {
            return symbol + " - " + name + ": $" + price;
        }
    }

    // --- Transaction Class ---
    static class Transaction {
        private String type; // BUY or SELL
        private Stock stock;
        private int quantity;

        public Transaction(String type, Stock stock, int quantity) {
            this.type = type;
            this.stock = stock;
            this.quantity = quantity;
        }

        public String toString() {
            return type + " " + quantity + " shares of " + stock.getSymbol() + " at $" + stock.getPrice();
        }
    }

    // --- User Class ---
    static class User {
        private String name;
        private double balance;
        private Map<String, Integer> portfolio;
        private List<Transaction> history;

        public User(String name, double balance) {
            this.name = name;
            this.balance = balance;
            this.portfolio = new HashMap<>();
            this.history = new ArrayList<>();
        }

        public void buyStock(Stock stock, int quantity) {
            double cost = stock.getPrice() * quantity;
            if (balance >= cost) {
                balance -= cost;
                portfolio.put(stock.getSymbol(), portfolio.getOrDefault(stock.getSymbol(), 0) + quantity);
                history.add(new Transaction("BUY", stock, quantity));
                System.out.println(" Bought " + quantity + " shares of " + stock.getSymbol());
            } else {
                System.out.println("Not enough balance to buy.");
            }
        }

        public void sellStock(Stock stock, int quantity) {
            int owned = portfolio.getOrDefault(stock.getSymbol(), 0);
            if (owned >= quantity) {
                balance += stock.getPrice() * quantity;
                portfolio.put(stock.getSymbol(), owned - quantity);
                history.add(new Transaction("SELL", stock, quantity));
                System.out.println("Sold " + quantity + " shares of " + stock.getSymbol());
            } else {
                System.out.println("Not enough shares to sell.");
            }
        }

        public void viewPortfolio(Map<String, Stock> market) {
            System.out.println("\n---  Your Portfolio ---");
            for (String symbol : portfolio.keySet()) {
                int qty = portfolio.get(symbol);
                Stock stock = market.get(symbol);
                double value = qty * stock.getPrice();
                System.out.println(symbol + ": " + qty + " shares - $" + value);
            }
            System.out.printf("Cash balance: $%.2f\n", balance);
        }

        public void viewHistory() {
            System.out.println("\n--- Transaction History ---");
            if (history.isEmpty()) {
                System.out.println("No transactions yet.");
            } else {
                for (Transaction t : history) {
                    System.out.println(t);
                }
            }
        }
    }

    // --- Main Program ---
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Market stocks
        Map<String, Stock> market = new HashMap<>();
        market.put("AAPL", new Stock("AAPL", "Apple", 150.00));
        market.put("GOOG", new Stock("GOOG", "Google", 2800.00));
        market.put("TSLA", new Stock("TSLA", "Tesla", 750.00));
        market.put("AMZN", new Stock("AMZN", "Amazon", 3400.00));

        // User setup
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        User user = new User(name, 10000); // Starting balance $10,000

        int choice;

        // Menu loop
        do {
            System.out.println("\n===  Stock Trading Menu ===");
            System.out.println("1. View Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Transaction History");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\n---  Market Stocks ---");
                    for (Stock stock : market.values()) {
                        System.out.println(stock);
                    }
                    break;

                case 2:
                    System.out.print("Enter stock symbol to buy: ");
                    String buySymbol = scanner.next().toUpperCase();
                    if (market.containsKey(buySymbol)) {
                        System.out.print("Enter quantity: ");
                        int qty = scanner.nextInt();
                        user.buyStock(market.get(buySymbol), qty);
                    } else {
                        System.out.println(" Stock not found.");
                    }
                    break;

                case 3:
                    System.out.print("Enter stock symbol to sell: ");
                    String sellSymbol = scanner.next().toUpperCase();
                    if (market.containsKey(sellSymbol)) {
                        System.out.print("Enter quantity: ");
                        int qty = scanner.nextInt();
                        user.sellStock(market.get(sellSymbol), qty);
                    } else {
                        System.out.println(" Stock not found.");
                    }
                    break;

                case 4:
                    user.viewPortfolio(market);
                    break;

                case 5:
                    user.viewHistory();
                    break;

                case 0:
                    System.out.println("Exiting... Thank you for using the Stock Trading App!");
                    break;

                default:
                    System.out.println(" Invalid choice. Please try again.");
            }

        } while (choice != 0);

        scanner.close();
    }
} 
