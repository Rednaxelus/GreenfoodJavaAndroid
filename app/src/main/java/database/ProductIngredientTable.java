package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ProductIngredientTable extends Table {
    private static final String TABLE_NAME = "product_ingredient";
    private static final String ID = "ID";
    private static final String ID_PRODUCT = "id_product";
    private static final String ID_INGREDIENT = "id_ingredient";

    public ProductIngredientTable(Context context) {

        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME
                + " (ID INTEGER PRIMARY KEY, " + ID_PRODUCT + " INTEGER,"
                + ID_INGREDIENT + " INTEGER, "
                + " FOREIGN KEY(" + ID_PRODUCT + ") REFERENCES product(id), "
                + " FOREIGN KEY(" + ID_INGREDIENT + ") REFERENCES ingredient(id))";
        db.execSQL(createTable);

    }


    public boolean addTuple(int idProduct, int idIngredient) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_PRODUCT, idProduct);
        contentValues.put(ID_INGREDIENT, idIngredient);
        if (sqlDB.insert(TABLE_NAME, null, contentValues) == -1) {
            return false;
        }
        return true;
    }


}
