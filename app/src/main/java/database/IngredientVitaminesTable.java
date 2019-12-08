package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.Vitamin;

public class IngredientVitaminesTable extends Table {

    private static final String TABLE_NAME = "ingredient_vitamines";
    private static final String ID = "ID";
    private static final String ID_INGREDIENT = "id_ingredient";
    private static final String VITAMINE = "vitamine";

    public IngredientVitaminesTable(Context context) {

        super(context, TABLE_NAME, null, 20);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + ID_INGREDIENT + " INT,"
                + VITAMINE + " TEXT DEFAULT ' '," +
                "  FOREIGN KEY(" + ID_INGREDIENT + ") REFERENCES ingredient(id))";
        db.execSQL(createTable);

    }

    public boolean addTuple(int idIngredient, List<Vitamin> vitamines) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        for (Vitamin vit : vitamines) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ID_INGREDIENT, idIngredient);
            contentValues.put(VITAMINE, vit.name());
            if (sqlDB.insert(TABLE_NAME, null, contentValues) == -1) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Vitamin> getVitaminesOfIngredient(int idIngredient) {
        ArrayList<Vitamin> vitamines = new ArrayList<>();
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_INGREDIENT + " = " + idIngredient;
        Cursor data = sqlDB.rawQuery(query, null);
        while (data.moveToNext()) {
            vitamines.add(Vitamin.valueOf(data.getString(2)));
        }
        data.close();
        return vitamines;
    }
}
