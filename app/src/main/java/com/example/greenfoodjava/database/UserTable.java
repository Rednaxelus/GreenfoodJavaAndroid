package com.example.greenfoodjava.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserTable extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "users";
    private static final String ID = "ID";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String LAST_NAME = "lastName";

    public UserTable(Context context) {
        super(context, TABLE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME
                + " (ID INTEGER PRIMARY KEY, " + USERNAME + " TEXT DEFAULT ' ', " + PASSWORD + " TEXT DEFAULT ' ', "
                + NAME + " TEXT DEFAULT ' ', " + LAST_NAME + " TEXT DEFAULT ' ')";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * New entry in user table.
     * @param userName username
     * @param password password
     * @param name name
     * @param lastName lastName
     * @return true//false
     */
    boolean addData(String userName, String password, String name, String lastName) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME, userName);
        contentValues.put(PASSWORD, password);
        contentValues.put(NAME, name);
        contentValues.put(LAST_NAME, lastName);

        long result = sqlDB.insert(TABLE_NAME, null, contentValues);
        return result == -1;
    }

    public boolean checkIfUserExist(String email) {
        return false;
    }
}
