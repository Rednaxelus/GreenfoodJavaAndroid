package model;

import java.util.List;

public class Dish {
    private int ID;
    private String name;
    private double price;
    private List<Ingredient> ingredients;

    public Dish(int ID, String name, double price, List<Ingredient> ingredients) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
    }
}
