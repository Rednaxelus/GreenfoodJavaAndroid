package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class IngredientVitaminesTable extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "ingredient_vitamines";
    private static final String ID = "ID";
    private static final String ID_INGREDIENT = "id_ingredient";
    private static final String VITAMINE = "vitamine";

    public IngredientVitaminesTable(Context context) {

        super(context, TABLE_NAME, null, 1);
        List<String> t = new ArrayList<>();
        t.add("A");
        t.add("B1");
        addTuple(4, t);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + ID_INGREDIENT + " INT,"
                + VITAMINE + " TEXT DEFAULT ' '," +
                "  FOREIGN KEY(" + ID_INGREDIENT + ") REFERENCES ingredient(id))";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addTuple(int idIngredient, List<String> vitamines) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        for (String vit : vitamines) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ID_INGREDIENT, idIngredient);
            contentValues.put(VITAMINE, vit);
            if (sqlDB.insert(TABLE_NAME, null, contentValues) == -1) {
                return false;
            }
        }
        return true;
    }
}
