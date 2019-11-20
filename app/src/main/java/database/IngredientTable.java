package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class IngredientTable extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "ingredient";
    private static final String ID = "ID";
    private static final String NAME = "name";
    private static final String AMOUNT = "amount";
    private static final String ENERGETIC_VALUE = "energetic_value";
    private static final String CALORIES = "calories";
    private static final String PROTEINS = "proteins";
    private static final String CARBOHYDRATES = "carbohydrates";
    private static final String FIBER = "fiber";
    private static final String FAT = "fat";
    IngredientVitaminesTable dbIngredientVitamine;
    IngredientAllergyTable dbIngredientAllergy;

    public IngredientTable(Context context) {

        super(context, TABLE_NAME, null, 1);

        dbIngredientVitamine = new IngredientVitaminesTable(context);
        dbIngredientAllergy = new IngredientAllergyTable(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME
                + " (ID INTEGER  PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT DEFAULT ' ',"
                + AMOUNT + " INT," + ENERGETIC_VALUE + " DOUBLE,"
                + CALORIES + " DOUBLE, " + PROTEINS + " DOUBLE, "
                + CARBOHYDRATES + " DOUBLE, " + FIBER + " TDOUBLE, "
                + FAT + " DOUBLE)";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addIngredient(String name, int amount, double energeticValue, double calories,
                                 double proteins, double carbohydrates, double fiber, double fat,
                                 List<String> vitamines, List<String> allergies) {
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
                dbIngredientAllergy.addTuple((int) result,allergies);
    }

    public int count() {
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = sqlDB.rawQuery(query, null);
        data.moveToFirst();
        int res = data.getCount();
        data.close();
        return res;
    }
}
