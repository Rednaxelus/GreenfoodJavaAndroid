package model;

import java.util.List;

public class Product {
    private String name, description;
    private double price;
    private int ID, stock;
    private List<Ingredient> ingredients;

    public Product(int ID, String name, String description, double price, int stock, List<Ingredient> ingredients) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
