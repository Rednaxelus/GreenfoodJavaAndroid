package model;

public class Product {
    private String ID, name, description;
    private double price;
    private int stock;

    public Product(String ID, String name, String description, double price, int stock) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
