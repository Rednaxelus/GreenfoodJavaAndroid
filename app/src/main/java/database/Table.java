package database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public abstract class Table extends SQLiteOpenHelper {
    private final String TABLE_NAME;

    public Table(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.TABLE_NAME = name;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    protected int count() {
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = sqlDB.rawQuery(query, null);
        data.moveToFirst();
        System.out.println(data.getCount());
        int res = data.getCount();
        data.close();
        return res;
    }
}
