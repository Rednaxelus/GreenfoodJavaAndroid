package model;

import java.util.ArrayList;
import java.util.List;

import logic.DietAnalyzer;

public class Product {
    private int ID;
    private String name;
    private String description;
    private double price;
    private int stock;
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

    public int getID() {
        return ID;
    }

    public String getDescription() {
        return description;
    }

    public int getStock() {
        return stock;
    }

    public Diet determineDietOfProduct() {
        return DietAnalyzer.determineDiet(ingredients);

    }

    public ArrayList<Allergy> getAllergiesOfProduct() {

        ArrayList<Allergy> allergies = new ArrayList<>();

        for (Ingredient ingredient :
                ingredients) {
            if (ingredient == null) {
                System.out.println("no ingredients in product: " + name);
                break;
            }
            for (Allergy allergy :
                    ingredient.getAllergies()) {
                if (!allergies.contains(allergy)) {
                    allergies.add(allergy);
                }
            }
        }
        return allergies;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

}
