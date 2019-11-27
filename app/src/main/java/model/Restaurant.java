package model;

import java.util.ArrayList;

public class Restaurant {
    private int ID;
    private String name;
    private String email;
    private String nif;
    private String description;
    private String phoneNumber;
    private String address;
    private ArrayList<Dish> dishes;

    public Restaurant(int ID, String name, String email, String nif, String description, String phoneNumber, String address, ArrayList<Dish> dishes) {
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.nif = nif;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.dishes = dishes;
    }

    public Diet determineDietOfRestaurant() {

        return DietAnalyzer.determineIncludedDietInDishes(dishes);
    }

    public Diet determineDietOfRestaurantByDescription() {

        if (description.contains("vegan")) {
            return Diet.VEGAN;
        } else if (description.contains("vegetarian")) {
            return Diet.VEGETARIAN;
        }

        return Diet.ALL;
    }

    public boolean hasDishesWithoutTheseAllergies(ArrayList<Allergy> allergiesToTest) {
        boolean includesAllergies;

        for (Dish dish :
                dishes) {
            includesAllergies = false;
            for (Allergy allergy :
                    dish.getAllergiesOfDish()) {
                if (allergiesToTest.contains(allergy)) {
                    includesAllergies = true;
                    break;
                }
            }
            if (!includesAllergies) {
                return true;
            }
        }

        return false;

    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }
}