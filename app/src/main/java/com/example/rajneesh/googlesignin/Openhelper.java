package com.example.rajneesh.googlesignin;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Openhelper extends SQLiteOpenHelper {
    private static Openhelper openHelper;

    public static Openhelper getInstance(Context context){
        if(openHelper == null){
            openHelper = new Openhelper(context.getApplicationContext());
        }
        return  openHelper;
    }

    private Openhelper(Context context) {
        super(context, Contract.DATABASE_NAME, null, Contract.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       // String expensesSql = "CREATE TABLE "+Contract.Events.TABLE_NAME+" ("+Contract.Events.ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+ Contract.Events.DATE+" DATE, " +Contract.Events.DESCRIPTION+" VARCHAR (500), "+Contract.Events.INITIATIVE+" TEXT)";
        db.execSQL("CREATE TABLE events(id INTEGER PRIMARY KEY  NOT NULL, date VARCHAR(10), description VARCHAR (500),initiative VARCHAR (10))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Contract.Events.TABLE_NAME);
        onCreate(db);

    }
}
