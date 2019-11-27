package model;

import java.util.ArrayList;
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

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Diet determineDietOfDish() {

        Diet diet = Diet.VEGAN;

        for (Ingredient ingredient : ingredients
        ) {
            if (ingredient.getName().contains("Carne") || ingredient.getName().contains("Pescado")) {
                return Diet.ALL;
            }
            if (diet != Diet.VEGETARIAN) {
                if (ingredient.getName().contains("Milk")) {
                    diet = Diet.VEGETARIAN;
                }
            }
        }

        return diet;
    }

    public ArrayList<Allergy> getAllergiesOfDish() {

        ArrayList<Allergy> allergies = new ArrayList<>();

        for (Ingredient ingredient :
                ingredients) {
            if (ingredient == null) {
                System.out.println("no ingredients in dish: " + name);
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
}
