package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.Allergy;
import model.Ingredient;
import model.Vitamin;

public class IngredientTable extends Table {

    private static final String TABLE_NAME = "ingredient";
    private static final String ID = "ID";  // the ids start with 1 and not 0?
    private static final String NAME = "name";
    private static final String AMOUNT = "amount";
    private static final String ENERGETIC_VALUE = "energetic_value";
    private static final String CALORIES = "calories";
    private static final String PROTEINS = "proteins";
    private static final String CARBOHYDRATES = "carbohydrates";
    private static final String FIBER = "fiber";
    private static final String FAT = "fat";
    private IngredientVitaminesTable dbIngredientVitamine;
    private AllergyTable dbAllergy;

    public IngredientTable(Context context) {

        super(context, TABLE_NAME, null, 9);

        dbIngredientVitamine = new IngredientVitaminesTable(context);
        dbAllergy = new AllergyTable(context);
        if (count() == 0)
            initDB();
    }

    private void initDB() {
        List<Vitamin> vitamines = new ArrayList<>();

        List<Allergy> allergies = new ArrayList<>();
        addIngredient("Lettuce", 3, 23, 34,
                23, 23, 12, 3,  vitamines, allergies);
        addIngredient("Tomato", 3, 23, 34,
                23, 23, 12, 3,  vitamines, allergies);
        addIngredient("Potato", 3, 23, 34,
                23, 23, 12, 3,  vitamines, allergies);
        addIngredient("Meat", 32, 23, 34,
                23, 23, 12, 3,  vitamines, allergies);

        allergies.add(Allergy.MILK);
        addIngredient("Milk", 3, 23, 34,
                23, 23, 12, 3,  vitamines, allergies);

        allergies.clear();
        allergies.add(Allergy.WHEAT);
        allergies.add(Allergy.GLUTEN);
        addIngredient("Pasta", 3, 23, 34,
                23, 23, 12, 3,  vitamines, allergies);

        allergies.clear();
        addIngredient("Egg", 3, 23, 34,
                23, 23, 12, 3,  vitamines, allergies);

        allergies.add(Allergy.GLUTEN);
        addIngredient("Flour", 3, 23, 34,
                23, 23, 12, 3,  vitamines, allergies);


        allergies.clear();
        addIngredient("Water", 3, 23, 34,
                23, 23, 12, 3,  vitamines, allergies);

        addIngredient("Fish", 3, 23, 34,
                23, 23, 12, 3,  vitamines, allergies);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME
                + " (ID INTEGER  PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT DEFAULT ' ',"
                + AMOUNT + " INT," + ENERGETIC_VALUE + " DOUBLE,"
                + CALORIES + " DOUBLE, " + PROTEINS + " DOUBLE, "
                + CARBOHYDRATES + " DOUBLE, " + FIBER + " DOUBLE, "
                + FAT + " DOUBLE)";
        db.execSQL(createTable);

    }


    public boolean addIngredient(String name, int amount, double energeticValue, double calories,
                                 double proteins, double carbohydrates, double fiber, double fat,
                                 List<Vitamin> vitamines, List<Allergy> allergies) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(AMOUNT, amount);
        contentValues.put(ENERGETIC_VALUE, energeticValue);
        contentValues.put(CALORIES, calories);
        contentValues.put(PROTEINS, proteins);
        contentValues.put(CARBOHYDRATES, carbohydrates);
        contentValues.put(FIBER, fiber);
        contentValues.put(FAT, fat);

        long result = sqlDB.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        return dbIngredientVitamine.addTuple((int) result,vitamines) &&
                dbAllergy.addTuple((int) result, allergies);
    }

    public List<Ingredient> getIngredients() {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = sqlDB.rawQuery(query, null);
        while (data.moveToNext()) {
            Ingredient in = new Ingredient(data.getString(1), data.getInt(2),
                    data.getInt(0), data.getDouble(3)+"",
                    data.getInt(4), data.getInt(5), data.getInt(6),
                    data.getInt(7), data.getInt(8), dbIngredientVitamine.getVitaminesOfIngredient(data.getInt(0)), dbAllergy.getAllergiesOfIngredient(data.getInt(0)));
            ingredients.add(in);
        }
        data.close();
        return ingredients;
    }

    public Ingredient getIngredient(String ingredientName) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + NAME + " = '" + ingredientName + "'";
        Cursor data = sqlDB.rawQuery(query, null);
        data.moveToFirst();
        Ingredient in = null;
        if (data.getCount() > 0)
            in = new Ingredient(ingredientName, data.getInt(2),
                    data.getInt(0), data.getDouble(3)+"",
                    data.getInt(4), data.getInt(5), data.getInt(6),
                    data.getInt(7), data.getInt(8), dbIngredientVitamine.getVitaminesOfIngredient(data.getInt(0)), dbAllergy.getAllergiesOfIngredient(data.getInt(0)));
        data.close();
        return in;
    }

    public Ingredient getIngredientByID(int ingredientId) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = '" + ingredientId + "'";
        Cursor data = sqlDB.rawQuery(query, null);
        data.moveToFirst();
        Ingredient in = null;
        if (data.getCount() > 0)
            in = new Ingredient(data.getString(1), data.getInt(2),
                    data.getInt(0), data.getDouble(3)+"",
                    data.getInt(4), data.getInt(5), data.getInt(6),
                    data.getInt(7), data.getInt(8), dbIngredientVitamine.getVitaminesOfIngredient(data.getInt(0)), dbAllergy.getAllergiesOfIngredient(data.getInt(0)));
        data.close();
        return in;
    }
}
