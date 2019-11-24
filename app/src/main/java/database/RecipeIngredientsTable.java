package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import model.Ingredient;

public class RecipeIngredientsTable extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "recipe_ingredient";
    private static final String ID = "ID";
    private static final String ID_RECIPE = "id_ingredient";
    private static final String ID_INGREDIENT = "id_ingredient";
    private IngredientTable dbIngredient;

    public RecipeIngredientsTable(Context context) {

        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + ID_RECIPE + " INT,"
                + ID_INGREDIENT + " INT,"
                + " FOREIGN KEY(" + ID_RECIPE + ") REFERENCES recipe(id), "
                + " FOREIGN KEY(" + ID_INGREDIENT + ") REFERENCES ingredient(id))";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addTuple(int idRecipe, int idIngredient) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_RECIPE, idRecipe);
        contentValues.put(ID_INGREDIENT, idIngredient);
        if (sqlDB.insert(TABLE_NAME, null, contentValues) == -1) {
            return false;
        }
        return true;
    }

    public List<Ingredient> getIngredientsOf(int recipeId) {
        System.out.println("recipe id = " + recipeId);
        List<Ingredient> ingredients = new ArrayList<>();
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_RECIPE + " = " + recipeId;
        Cursor data = sqlDB.rawQuery(query, null);
        while (data.moveToNext()) {
            ingredients.add(dbIngredient.getIngredientByID(data.getInt(1)));
        }
        data.close();
        return ingredients;
    }
}
