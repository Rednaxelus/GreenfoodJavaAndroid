package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import data.model.Ingredient;

public class PlateTable extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "plate";
    private static final String ID = "ID";
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private PlateIngredientTable dbPlateIngredient;

    public PlateTable(Context context) {
        super(context, TABLE_NAME, null, 1);
        dbPlateIngredient = new PlateIngredientTable(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME
                + " (ID INTEGER  PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT DEFAULT ' ',"
                + PRICE + " DOUBLE )";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addPlate(String name, double price, List<Ingredient> ingredients) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(PRICE, price);

        long result = sqlDB.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        for (Ingredient ingredient : ingredients) {
            if (!dbPlateIngredient.addTuple((int) result, ingredient.getId()))
                return false;
        }
        return true;
    }
}
