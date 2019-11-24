package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import model.Ingredient;

public class RecipeTable extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "recipe";
    private static final String ID = "ID";
    private static final String ID_USER = "id_user";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String DURATION = "duration";
    private static final String STEPS = "steps";
    private static final String PHOTO = "photo";
    private RecipeIngredientsTable dbRecipeIngredient;

    public RecipeTable(Context context) {
        super(context, TABLE_NAME, null, 2);
        dbRecipeIngredient = new RecipeIngredientsTable(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME
                + " (ID INTEGER  PRIMARY KEY AUTOINCREMENT, " + ID_USER + " INTEGER,"
                + NAME + " TEXT DEFAULT ' ', " + DESCRIPTION + " TEXT DEFAULT ' ',"
                + DURATION + " INTEGER ,"+ STEPS + " TEXT DEFAULT ' ',"
                + PHOTO + " TEXT DEFAULT ' ',"
                + " FOREIGN KEY(" + ID_USER + ") REFERENCES users(id))";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addRecipe(int idUser, String name, String description, int duration,
                             String steps, String photo, List<Ingredient> ingredients) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_USER, idUser);
        contentValues.put(NAME, name);
        contentValues.put(DESCRIPTION, description);
        contentValues.put(DURATION, duration);
        contentValues.put(STEPS, steps);
        contentValues.put(PHOTO, photo);

        long result = sqlDB.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        for (Ingredient ingredient : ingredients) {
            if (!dbRecipeIngredient.addTuple((int) result, ingredient.getId()))
                return false;
        }
        return true;
    }
}
