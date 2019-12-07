package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.Ingredient;
import model.Product;

public class ProductTable extends Table {

    private static final String TABLE_NAME = "product";
    private static final String ID = "ID";
    private static final String ID_ENTERPRISE = "id_enterprise";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String PRICE = "price";
    private static final String STOCK = "stock";
    private ProductIngredientTable dbProductIngredientTable;
    private IngredientTable ingredientTable;

    public ProductTable(Context context) {
        super(context, TABLE_NAME, null, 16);
        dbProductIngredientTable = new ProductIngredientTable(context);
        ingredientTable = new IngredientTable(context);

        if (count() == 0) addFillerEntries();
    }

    private void addFillerEntries() {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredientTable.getIngredient("Meat"));

        addProduct("Canned Carne", "very tasty for everyone who lives", 4.49, 213, ingredients, 1);

        ingredients.clear();
        ingredients.add(ingredientTable.getIngredient("Milk"));
        addProduct("MilkyMilk", "now very new", 1.19, 43, ingredients, 1);
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
            if (!dbProductIngredientTable.addTuple((int) result, ingredient.getId()))
                return false;
        }
        return true;
    }

    public Cursor searchByName(String name) {
        System.out.println(name);
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String query = "SELECT " + TABLE_NAME + ".*," + TABLE_NAME + ".id as _id FROM " + TABLE_NAME + " WHERE " + NAME + " LIKE '" + "%" + name + "%" + "'";
        Cursor data = sqlDB.rawQuery(query, null);
        data.moveToFirst();
        return data;
    }

    public List<Product> getProducts(int enterpriseId) {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_ENTERPRISE + " = " + enterpriseId;
        Cursor data = sqlDB.rawQuery(query, null);
        if (data.getCount() == 0) return products;
        while (data.moveToNext()) {
            System.out.println(data.getString(3));
            products.add(new Product(data.getInt(0), data.getString(2),
                    data.getString(3), data.getDouble(4), data.getInt(5),
                    dbProductIngredientTable.getIngredientsOf(data.getInt(1))));
        }
        data.close();
        return products;
    }

    public void deleteFromDatabase(Product product) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String query = "DELETE FROM "+ TABLE_NAME + " WHERE " + ID + " = " + product.getID();
        sqlDB.execSQL(query);
    }

    public ArrayList<Product> getProductWithName(String query) {
        Cursor data = searchByName(query);
        data.moveToFirst();

        ArrayList<Product> products = new ArrayList<>();

        while (!data.isAfterLast()) {

            ArrayList<Ingredient> ingredients = dbProductIngredientTable.getIngredientsOf(data.getInt(data.getColumnIndex(ID)));
            products.add(new Product(data.getInt(data.getColumnIndex(ID)), data.getString(data.getColumnIndex(NAME)), data.getString(data.getColumnIndex(DESCRIPTION))
                    , data.getDouble(data.getColumnIndex(PRICE)), data.getInt(data.getColumnIndex(STOCK)), ingredients));

            data.moveToNext();
        }
        return products;
    }
}


