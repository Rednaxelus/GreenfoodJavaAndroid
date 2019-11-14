package com.example.greenfoodjava.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EnterpriseTable extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "enterprise";
    private static final String ID = "ID";
    private static final String NAME = "name";
    private static final String NIF = "nif";
    private static final String PASSWORD = "password";
    private static final String DESCRIPTION = "description";
    private static final String PHONE_NUMBER = "phoneNumber";
    private static final String ADDRESS = "address";


    EnterpriseTable(Context context) {
        super(context, TABLE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME
                + " (ID INTEGER PRIMARY KEY, " + NAME + " TEXT DEFAULT ' ',"
                + PASSWORD + " TEXT DEFAULT ' ', " + NIF + " TEXT DEFAULT ' ', "
                + DESCRIPTION + " TEXT DEFAULT ' ', " + PHONE_NUMBER + " TEXT DEFAULT ' ', "
                + ADDRESS + " TEXT DEFAULT ' ')";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * New entry in user table.
     * @param name name
     * @param nif nif
     * @param password password
     * @param description description
     * @param phoneNumber phoneNumber
     * @param address address
     * @return true//false
     */
    boolean addData(String name, String nif, String password, String description, String phoneNumber,String address) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(NIF, nif);
        contentValues.put(PASSWORD, password);
        contentValues.put(NAME, description);
        contentValues.put(PHONE_NUMBER, phoneNumber);
        contentValues.put(ADDRESS, address);

        long result = sqlDB.insert(TABLE_NAME, null, contentValues);
        return result == -1;
    }
}
