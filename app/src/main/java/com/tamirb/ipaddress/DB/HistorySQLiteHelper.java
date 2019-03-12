package com.tamirb.ipaddress.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class HistorySQLiteHelper extends SQLiteOpenHelper {

    public HistorySQLiteHelper(Context context,
                               String name,
                               SQLiteDatabase.CursorFactory factory,
                               int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create the tables of this DB - first time
        db.execSQL("create table " +
                DBConstants.IP_TABLE_NAME +
                "(" + DBConstants.IP_CITY_COLUMN + " TEXT, " +
                DBConstants.IP_COUNTRY_COLUMN + " TEXT, " +
                DBConstants.IP_COUNTRY_CODE_COLUMN + " TEXT, " +
                DBConstants.IP_ISP_COLUMN + " TEXT, " +
                DBConstants.IP_LAT_COLUMN + " REAL, " +
                DBConstants.IP_LON_COLUMN + " REAL, " +
                DBConstants.IP_ORG_COLUMN + " TEXT, " +
                DBConstants.IP_IP_COLUMN + " TEXT PRIMARY KEY, " +
                DBConstants.IP_REGION_COLUMN + " TEXT, " +
                DBConstants.IP_REGION_NAME_COLUMN + " TEXT, " +
                DBConstants.IP_ZIP_COLUMN + " TEXT, " +
                DBConstants.IP_DATE_COLUMN + " TEXT );");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
