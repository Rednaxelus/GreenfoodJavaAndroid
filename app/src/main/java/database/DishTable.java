package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import model.Allergy;
import model.Diet;
import model.Dish;
import model.Ingredient;

public class DishTable extends Table {

    private static final String TABLE_NAME = "DISH";
    private static final String ID = "ID";
    private static final String ID_ENTERPRISE = "id_enterprise";
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private DishIngredientTable dbPlateIngredient;

    public DishTable(Context context) {
        super(context, TABLE_NAME, null, 2);
        dbPlateIngredient = new DishIngredientTable(context);

        ArrayList<Ingredient> temp = new ArrayList<>();

        temp.add(dbPlateIngredient.getDbIngredient().getIngredient("Carne"));

        // addDish("Chili con carne", 18, temp, 0);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME
                + " (ID INTEGER  PRIMARY KEY AUTOINCREMENT, " + ID_ENTERPRISE + " INTEGER , "
                + NAME + " TEXT DEFAULT ' '," + PRICE + " DOUBLE,"
                + " FOREIGN KEY(" + ID_ENTERPRISE + ") REFERENCES enterprise(id)) ";
        db.execSQL(createTable);
    }

    public boolean addDish(String name, double price, List<Ingredient> ingredients, int idEnterprise) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(ID_ENTERPRISE, idEnterprise);
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

    public List<Dish> getDishes(int enterpriseId) {
        List<Dish> dishes = new ArrayList<>();
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_ENTERPRISE + " = " + enterpriseId;
        Cursor data = sqlDB.rawQuery(query, null);
        if (data.getCount() == 0) return dishes;
        while (data.moveToNext()) {
            System.out.println(data.getString(3));
            dishes.add(new Dish(data.getInt(0), data.getString(2),
                    data.getDouble(3), dbPlateIngredient.getIngredientsOf(data.getInt(1))));
        }
        data.close();
        //dishes.add(new Dish(1, "Macarrones con tomate", 12, null));
        return dishes;
    }

    public Cursor searchByName(String name) {
        System.out.println(name);
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        //String query = "SELECT "+ TABLE_NAME + ".*,"+TABLE_NAME+".id as _id FROM " + TABLE_NAME + " WHERE " + NAME + " LIKE '" + name+"%" + "' AND " + TYPE + " = '" + "Restaurant'";
        String query = "SELECT " + TABLE_NAME + ".*," + TABLE_NAME + ".id as _id FROM " + TABLE_NAME + " WHERE " + NAME + " LIKE '" + name + "%" + "'";
        Cursor data = sqlDB.rawQuery(query, null);
        data.moveToFirst();
        return data;
    }

    public ArrayList<Dish> searchByNameAndFilter(String name, ArrayList<Allergy> allergies, Diet diet) {
        ArrayList<String> dishIDs = new ArrayList<>();

        if (allergies != null) {

            Cursor res = searchByName(name);
            while (!res.isAfterLast()) {
                ArrayList<Allergy> allergiesRes = getAllergiesOfDish(res.getInt(res.getColumnIndex(ID)));
                for (Allergy allergy : allergiesRes
                ) {
                    if (allergies.contains(allergy)) {
                        dishIDs.add("" + res.getInt(res.getColumnIndex(ID)));
                        break;
                    }
                }
                res.moveToNext();
            }
        }

        System.out.println("filtered Dishes: " + dishIDs);

        SQLiteDatabase sqlDB = this.getWritableDatabase();

        String args = "(";

        for (String dishID : dishIDs
        ) {
            args += dishID;
            if (dishIDs.get(dishIDs.size() - 1) != dishID) {
                args += ", ";
            }
        }
        args += ")";


        //  Cursor data = sqlDB.query(TABLE_NAME, null, "ID=?", dishIDs.toArray(new String[dishIDs.size()]), null, null, null);
        String query = "SELECT " + TABLE_NAME + ".*," + TABLE_NAME + ".id as _id FROM " + TABLE_NAME + " WHERE " + NAME + " LIKE '" + name + "%" + "' AND " + ID + " NOT IN " + args;
        Cursor data = sqlDB.rawQuery(query, null);
        data.moveToFirst();

        ArrayList<Dish> dishes = new ArrayList<>();

        while (!data.isAfterLast()) {

            dishes.add(new Dish(data.getInt(data.getColumnIndex(ID)), data.getString(data.getColumnIndex(NAME)), data.getDouble(data.getColumnIndex(PRICE)), dbPlateIngredient.getIngredientsOf(data.getInt(data.getColumnIndex(ID)))));

            data.moveToNext();
        }

        ListIterator litr = dishes.listIterator();

        while (litr.hasNext()) {
            Dish tempDish = (Dish) litr.next();
            if (tempDish.determineDietOfDish().ordinal() < diet.ordinal()) {
                litr.remove();
            }
        }

        return dishes;
    }

    public ArrayList<Allergy> getAllergiesOfDish(int id) {

        ArrayList<Allergy> allergies = new ArrayList<>();

        for (Ingredient ingredient :
                dbPlateIngredient.getIngredientsOf(id)) {
            if (ingredient == null) {
                System.out.println("no ingredients in dish: " + id);
                break;
            }
            ArrayList<Allergy> result = dbPlateIngredient.getDbIngredient().getDbIngredientAllergy().getAllergiesOfIngredient(ingredient.getId());
            for (Allergy allergy :
                    result) {
                if (!allergies.contains(allergy)) {
                    allergies.add(allergy);
                }
            }
        }
        return allergies;
    }

}
