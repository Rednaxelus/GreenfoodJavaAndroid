package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import model.Ingredient;

public class ProductTable extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "product";
    private static final String ID = "ID";
    private static final String ID_ENTERPRISE = "id_enterprise";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String PRICE = "price";
    private static final String STOCK = "stock";
    private ProductIngredientTable dbProductIngredientTable;

    public ProductTable(Context context) {

        super(context, TABLE_NAME, null, 2);
        dbProductIngredientTable = new ProductIngredientTable(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME
                + " (ID INTEGER PRIMARY KEY, " + ID_ENTERPRISE + " INTEGER, " + NAME + " TEXT DEFAULT ' ',"
                + DESCRIPTION + " TEXT DEFAULT ' ', " + PRICE + " DOUBLE, "
                    + STOCK + " INT,"
                + " FOREIGN KEY(" + ID_ENTERPRISE + ") REFERENCES enterprise(id)) ";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addProduct(String name, String description, double price, int stock,
                              List<Ingredient> ingredients, int idEnterprise) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(ID_ENTERPRISE, idEnterprise);
        contentValues.put(DESCRIPTION, description);
        contentValues.put(PRICE, price);
        contentValues.put(STOCK, stock);
        long result = sqlDB.insert(TABLE_NAME, null, contentValues);

        if (result == -1)
            return false;
        for (Ingredient ingredient : ingredients) {
            System.out.println((int)result);
            if (!dbProductIngredientTable.addTuple((int) result, ingredient.getId()))
                return false;
        }
        return true;
    }
}

