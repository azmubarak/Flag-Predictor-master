package com.example.flag_1;

import android.app.AppComponentFactory;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.support.v7.app.AppCompatActivity;

public class DBHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "MyDBName.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table flag " +
                        "(id integer primary key autoincrement , country text,imageId integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS flag");
        onCreate(db);
    }

    public void insertFlag (String country, int imageId) {

        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO flag VALUES (NULL, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, country);
        statement.bindDouble(2, imageId);

        statement.executeInsert();

    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }
}
