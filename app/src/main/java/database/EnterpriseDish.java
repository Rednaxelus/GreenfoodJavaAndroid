package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EnterpriseDish extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "enterprise_dish";
    private static final String ID = "ID";
    private static final String ID_ENTERPRISE = "id_enterprise";
    private static final String ID_DISH = "id_dish";

    public EnterpriseDish(Context context) {

        super(context, TABLE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + ID_DISH + " INT,"
                + ID_ENTERPRISE + " INT,"
                + " FOREIGN KEY(" + ID_ENTERPRISE + ") REFERENCES enterprise(id), "
                + " FOREIGN KEY(" + ID_DISH + ") REFERENCES DISH(id))";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addTuple(int idEnterprise, int idDish) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_ENTERPRISE, idEnterprise);
        contentValues.put(ID_DISH, idDish);
        if (sqlDB.insert(TABLE_NAME, null, contentValues) == -1) {
            return false;
        }
        return true;
    }
}
