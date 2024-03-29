package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EnterpriseTable extends Table {

    private static final String TABLE_NAME = "enterprise";
    private static final String ID = "ID";
    private static final String EMAIL = "email";
    private static final String NAME = "name";
    private static final String NIF = "nif";
    private static final String PASSWORD = "password";
    private static final String DESCRIPTION = "description";
    private static final String PHONE_NUMBER = "phoneNumber";
    private static final String ADDRESS = "address";
    private static final String TYPE = "type";


    public EnterpriseTable(Context context) {
        super(context, TABLE_NAME, null, 2);
        System.out.println(count());
        if (count() == 0)
            addData("test6@h.com", "Rest","13518001G","jjj","kjhfsjsdfkj",
                    "888","C/DD","Restaurant");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + EMAIL + " TEXT DEFAULT ' '," + NAME + " TEXT DEFAULT ' ',"
                + PASSWORD + " TEXT DEFAULT ' ', " + NIF + " TEXT DEFAULT ' ', "
                + DESCRIPTION + " TEXT DEFAULT ' ', " + PHONE_NUMBER + " TEXT DEFAULT ' ', "
                + ADDRESS + " TEXT DEFAULT ' ', " +  TYPE + " TEXT DEFAULT ' ')";
        db.execSQL(createTable);
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
    public boolean addData(String email, String name, String nif, String password, String description,
                    String phoneNumber,String address, String type) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(EMAIL, email);
        contentValues.put(NAME, name);
        contentValues.put(PASSWORD, getMD5(password));
        contentValues.put(NIF, nif);
        contentValues.put(DESCRIPTION, description);
        contentValues.put(PHONE_NUMBER, phoneNumber);
        contentValues.put(ADDRESS, address);
        contentValues.put(TYPE, type);

        long result = sqlDB.insert(TABLE_NAME, null, contentValues);

        return result == -1;
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

    public boolean checkIfEnterpriseExist(String email) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + EMAIL + " = '" + email + "'";
        Cursor data = sqlDB.rawQuery(query, null);
        data.moveToFirst();
        boolean res = data.getCount() > 0;
        data.close();
        return res;
    }

    public int checkIfUserExist(String email, String password) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + EMAIL + " = '" + email + "' AND "
                + PASSWORD + " = '" + getMD5(password) + "'";
        Cursor data = sqlDB.rawQuery(query, null);
        data.moveToFirst();
        int id;
        if (data.getCount() == 0)id = -1;
        else id = data.getInt(0);

        data.close();
        return id;
    }

    public Cursor searchByRestaurantName(String search){
        System.out.println(search);
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String query = "SELECT "+ TABLE_NAME + ".*,"+TABLE_NAME+".id as _id FROM " + TABLE_NAME + " WHERE " + NAME + " LIKE '" + search+"%" + "' AND " + TYPE + " = '" + "Restaurant'";
        Cursor data = sqlDB.rawQuery(query, null);
        return data;
    }



}
