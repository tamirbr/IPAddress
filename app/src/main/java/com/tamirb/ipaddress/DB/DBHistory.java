package com.tamirb.ipaddress.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tamirb.ipaddress.Model.IpInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


public class DBHistory {
    HistorySQLiteHelper myHelper;
    SQLiteDatabase mydb;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date newDate;

    public DBHistory(Context context)
    {
        myHelper = new HistorySQLiteHelper(context,
                DBConstants.IP_DB_NAME,
                null, DBConstants.IP_DB_VERSION);
    }

    public IpInfo getByIp(String ip) {
        SQLiteDatabase db = myHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DBConstants.IP_TABLE_NAME + " WHERE " + DBConstants.IP_IP_COLUMN +" = '"+ip+"'";
        Cursor cursor = db.rawQuery(query, new String[] {});
        IpInfo ipInfo = null;
        if (cursor.moveToFirst()){
            ipInfo = new IpInfo();
            ipInfo.setCity       (cursor.getString(cursor.getColumnIndex(DBConstants.IP_CITY_COLUMN        )));
            ipInfo.setCountry    (cursor.getString(cursor.getColumnIndex(DBConstants.IP_COUNTRY_COLUMN     )));
            ipInfo.setCountryCode(cursor.getString(cursor.getColumnIndex(DBConstants.IP_COUNTRY_CODE_COLUMN)));
            ipInfo.setIsp        (cursor.getString(cursor.getColumnIndex(DBConstants.IP_ISP_COLUMN         )));
            ipInfo.setLat        (cursor.getDouble(cursor.getColumnIndex(DBConstants.IP_LAT_COLUMN         )));
            ipInfo.setLon        (cursor.getDouble(cursor.getColumnIndex(DBConstants.IP_LON_COLUMN         )));
            ipInfo.setOrg        (cursor.getString(cursor.getColumnIndex(DBConstants.IP_ORG_COLUMN         )));
            ipInfo.setIp         (cursor.getString(cursor.getColumnIndex(DBConstants.IP_IP_COLUMN          )));
            ipInfo.setRegion     (cursor.getString(cursor.getColumnIndex(DBConstants.IP_REGION_COLUMN      )));
            ipInfo.setRegionName (cursor.getString(cursor.getColumnIndex(DBConstants.IP_REGION_NAME_COLUMN )));
            ipInfo.setZip        (cursor.getString(cursor.getColumnIndex(DBConstants.IP_ZIP_COLUMN         )));
            try{
                ipInfo.setDateSearched(DBConstants.SDF.parse(cursor.getString(cursor.getColumnIndex(DBConstants.IP_DATE_COLUMN))));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return ipInfo;
    }

    public void add(IpInfo ipInfo) {
        mydb = myHelper.getWritableDatabase();

        ContentValues myRowValues = new ContentValues();
        myRowValues.put(DBConstants.IP_CITY_COLUMN        , ipInfo.getCity          ());
        myRowValues.put(DBConstants.IP_COUNTRY_COLUMN     , ipInfo.getCountry       ());
        myRowValues.put(DBConstants.IP_COUNTRY_CODE_COLUMN, ipInfo.getCountryCode   ());
        myRowValues.put(DBConstants.IP_ISP_COLUMN         , ipInfo.getIsp           ());
        myRowValues.put(DBConstants.IP_LAT_COLUMN         , ipInfo.getLat           ());
        myRowValues.put(DBConstants.IP_LON_COLUMN         , ipInfo.getLon           ());
        myRowValues.put(DBConstants.IP_ORG_COLUMN         , ipInfo.getOrg           ());
        myRowValues.put(DBConstants.IP_IP_COLUMN          , ipInfo.getIp            ());
        myRowValues.put(DBConstants.IP_REGION_COLUMN      , ipInfo.getRegion        ());
        myRowValues.put(DBConstants.IP_REGION_NAME_COLUMN , ipInfo.getRegionName    ());
        myRowValues.put(DBConstants.IP_ZIP_COLUMN         , ipInfo.getZip           ());
        myRowValues.put(DBConstants.IP_DATE_COLUMN        , DBConstants.SDF.format(new Date()));

        if(getByIp(ipInfo.getIp()) != null){
            mydb.update(DBConstants.IP_TABLE_NAME, myRowValues,DBConstants.IP_IP_COLUMN + "='" + ipInfo.getIp() +"'", null);
        } else{
            mydb.insert(DBConstants.IP_TABLE_NAME, null, myRowValues);
        }
    }

    public void delete(String ipAddress) {
        mydb = myHelper.getWritableDatabase();
        mydb.delete(DBConstants.IP_TABLE_NAME, DBConstants.IP_IP_COLUMN + "='" + ipAddress+"'", null);
    }

    public ArrayList<IpInfo> getAllIpSearches()
    {
        ArrayList<IpInfo> allIpSearches = new ArrayList<IpInfo>();
        SQLiteDatabase db = myHelper.getReadableDatabase();

        Cursor cursor = db.query(DBConstants.IP_TABLE_NAME,
                null, null, null, null, null, null);
        IpInfo ipInfo = null;
        while(cursor.moveToNext()) {
            ipInfo = new IpInfo();
            ipInfo.setCity       (cursor.getString(cursor.getColumnIndex(DBConstants.IP_CITY_COLUMN        )));
            ipInfo.setCountry    (cursor.getString(cursor.getColumnIndex(DBConstants.IP_COUNTRY_COLUMN     )));
            ipInfo.setCountryCode(cursor.getString(cursor.getColumnIndex(DBConstants.IP_COUNTRY_CODE_COLUMN)));
            ipInfo.setIsp        (cursor.getString(cursor.getColumnIndex(DBConstants.IP_ISP_COLUMN         )));
            ipInfo.setLat        (cursor.getDouble(cursor.getColumnIndex(DBConstants.IP_LAT_COLUMN         )));
            ipInfo.setLon        (cursor.getDouble(cursor.getColumnIndex(DBConstants.IP_LON_COLUMN         )));
            ipInfo.setOrg        (cursor.getString(cursor.getColumnIndex(DBConstants.IP_ORG_COLUMN         )));
            ipInfo.setIp         (cursor.getString(cursor.getColumnIndex(DBConstants.IP_IP_COLUMN          )));
            ipInfo.setRegion     (cursor.getString(cursor.getColumnIndex(DBConstants.IP_REGION_COLUMN      )));
            ipInfo.setRegionName (cursor.getString(cursor.getColumnIndex(DBConstants.IP_REGION_NAME_COLUMN )));
            ipInfo.setZip        (cursor.getString(cursor.getColumnIndex(DBConstants.IP_ZIP_COLUMN         )));
            try{
                ipInfo.setDateSearched(DBConstants.SDF.parse(cursor.getString(cursor.getColumnIndex(DBConstants.IP_DATE_COLUMN))));
            }catch (Exception e){
                e.printStackTrace();
            }
            allIpSearches.add(ipInfo);
        }
        Collections.reverse(allIpSearches);
        return allIpSearches;
    }
}
