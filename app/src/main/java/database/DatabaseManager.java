package database;

public class DatabaseManager {
    private static DishTable dishTable;
    private static UserTable userTable;
    private static IngredientTable ingredientTable;


    public static void createAllTables(DishTable t1, UserTable t2, IngredientTable t3) {
        dishTable = t1;
        userTable = t2;
        ingredientTable = t3;
    }

    public static DishTable getDishTable() {
        return dishTable;
    }

    public static UserTable getUserTable() {
        return userTable;
    }

    public static IngredientTable getIngredientTable() {
        return ingredientTable;
    }
}
