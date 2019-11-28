package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.Allergy;

public class IngredientAllergyTable extends Table {

    private static final String TABLE_NAME = "ingredient_allergy";
    private static final String ID = "ID";
    private static final String ID_INGREDIENT = "id_ingredient";
    private static final String ALLERGY = "allergy";

    public IngredientAllergyTable(Context context) {

        super(context, TABLE_NAME, null, 3);
        List<String> t = new ArrayList<>();
        t.add("A");
        t.add("B1");
        addTuple(4, t);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + ID_INGREDIENT + " INT,"
                + ALLERGY + " TEXT DEFAULT ' '," +
                "  FOREIGN KEY(" + ID_INGREDIENT + ") REFERENCES ingredient(id))";
        db.execSQL(createTable);

    }


    public boolean addTuple(int idIngredient, List<String> allergies) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        for (String allergy : allergies) {
            System.out.println(allergy);
            ContentValues contentValues = new ContentValues();
            contentValues.put(ID_INGREDIENT, idIngredient);
            contentValues.put(ALLERGY, allergy);
            if (sqlDB.insert(TABLE_NAME, null, contentValues) == -1) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Allergy> getAllergiesOfIngredient(int idIngredient) {
        ArrayList<Allergy> allergies = new ArrayList<>();
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_INGREDIENT + " = " + idIngredient;
        Cursor data = sqlDB.rawQuery(query, null);
        while (data.moveToNext()) {
            allergies.add(Allergy.valueOf(data.getString(2)));
        }
        data.close();
        return allergies;
    }
}

