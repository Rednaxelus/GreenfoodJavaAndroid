package database;

public class DatabaseManager {
    private static DishTable dishTable;

    public static DishTable getDishTable() {
        return dishTable;
    }

    public static void setDishTable(DishTable dishTable) {
        DatabaseManager.dishTable = dishTable;
    }
}
