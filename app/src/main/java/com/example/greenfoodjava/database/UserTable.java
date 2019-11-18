package com.example.greenfoodjava.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class UserTable extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "users";
    private static final String ID = "ID";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String LAST_NAME = "lastName";

    public UserTable(Context context) {
        super(context, TABLE_NAME, null, 1);

        addData("dd@ff.com","ddd","Jose","ddddd");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME
                + " (ID INTEGER PRIMARY KEY, " + EMAIL + " TEXT DEFAULT ' ', "
                + PASSWORD + " TEXT DEFAULT ' ', " + NAME + " TEXT DEFAULT ' ', "
                + LAST_NAME + " TEXT DEFAULT ' ')";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * New entry in user table.
     * @param email email
     * @param password password
     * @param name name
     * @param lastName lastName
     * @return true//false
     */
    public boolean addData(String email, String password,  String name, String lastName) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(EMAIL, email);
        contentValues.put(PASSWORD, getMD5(password));
        contentValues.put(NAME, name);
        contentValues.put(LAST_NAME, lastName);

        long result = sqlDB.insert(TABLE_NAME, null, contentValues);
        return result == -1;
    }

    public boolean checkIfUserExist(String email) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + EMAIL + " = '" + email + "'";
        Cursor data = sqlDB.rawQuery(query, null);
        data.moveToFirst();
        boolean res = data.getCount() > 0;
        data.close();
        return res;
    }

    private String getMD5(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] messageDigest = md.digest(password.getBytes());
        BigInteger number = new BigInteger(1, messageDigest);
        return number.toString(32);
    }
}
