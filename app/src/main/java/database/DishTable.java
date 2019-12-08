package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.Dish;
import model.Ingredient;

public class DishTable extends Table {

    private static final String TABLE_NAME = "DISH";
    private static final String ID = "ID";
    private static final String ID_ENTERPRISE = "id_enterprise";
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private DishIngredientTable dbDishIngredient;

    public DishTable(Context context) {
        super(context, TABLE_NAME, null, 25);
        dbDishIngredient = new DishIngredientTable(context);

        if (count() == 0) {
            addFillerEntries();
        }

    }

    private void addFillerEntries() {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(dbDishIngredient.getDbIngredient().getIngredient("Tomato"));

        addDish("Creme de Tomaté", 3.50, ingredients, 3);

        ingredients.clear();

        ingredients.add(dbDishIngredient.getDbIngredient().getIngredient("Peanuts"));

        addDish("Pumpkin stew with peanuts", 6.99, ingredients, 3);

        ingredients.clear();

        ingredients.add(dbDishIngredient.getDbIngredient().getIngredient("Meat"));
        ingredients.add(dbDishIngredient.getDbIngredient().getIngredient("Bread"));
        ingredients.add(dbDishIngredient.getDbIngredient().getIngredient("Lettuce"));
        addDish("Beefy pan", 8.99, ingredients, 2);
        ingredients.clear();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME
                + " (ID INTEGER  PRIMARY KEY AUTOINCREMENT, " + ID_ENTERPRISE + " INTEGER , "
                + NAME + " TEXT DEFAULT ' '," + PRICE + " DOUBLE,"
                + " FOREIGN KEY(" + ID_ENTERPRISE + ") REFERENCES enterprise(id)) ";
        db.execSQL(createTable);
    }

    public boolean addDish(String name, double price, List<Ingredient> ingredients, int idEnterprise) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(ID_ENTERPRISE, idEnterprise);
        contentValues.put(NAME, name);
        contentValues.put(PRICE, price);

        long result = sqlDB.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        for (Ingredient ingredient : ingredients) {
            if (!dbDishIngredient.addTuple((int) result, ingredient.getId()))
                return false;
        }
        return true;
    }

    public List<Dish> getDishes(int enterpriseId) {
        List<Dish> dishes = new ArrayList<>();
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_ENTERPRISE + " = " + enterpriseId;
        Cursor data = sqlDB.rawQuery(query, null);
        if (data.getCount() == 0) return dishes;
        while (data.moveToNext()) {
            dishes.add(new Dish(data.getInt(0), data.getString(2),
                    data.getDouble(3), dbDishIngredient.getIngredientsOf(data.getInt(0))));
        }
        data.close();
        //dishes.add(new Dish(1, "Macarrones con tomate", 12, null));
        return dishes;
    }

    private Cursor searchByName(String name) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String query = "SELECT " + TABLE_NAME + ".*," + TABLE_NAME + ".id as _id FROM " + TABLE_NAME + " WHERE " + NAME + " LIKE '" + "%" + name + "%" + "'";
        Cursor data = sqlDB.rawQuery(query, null);
        data.moveToFirst();
        return data;
    }

    public ArrayList<Dish> getDishesWithName(String name) {
        Cursor data = searchByName(name);
        data.moveToFirst();

        ArrayList<Dish> dishes = new ArrayList<>();

        while (!data.isAfterLast()) {

            ArrayList<Ingredient> ingredients = dbDishIngredient.getIngredientsOf(data.getInt(data.getColumnIndex(ID)));
            dishes.add(new Dish(data.getInt(data.getColumnIndex(ID)), data.getString(data.getColumnIndex(NAME)), data.getDouble(data.getColumnIndex(PRICE)), ingredients));

            data.moveToNext();
        }
        return dishes;
    }

}
