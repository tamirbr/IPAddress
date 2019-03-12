package com.tamirb.ipaddress.DB;

import java.text.SimpleDateFormat;

public class DBConstants {
    // History list db
    public static String IP_DB_NAME = "ip_history.db";
    public static int IP_DB_VERSION = 1;
    public static String IP_TABLE_NAME = "ip_info";
    public static String IP_ID_COLUMN           = "id";
    public static String IP_CITY_COLUMN         = "city";
    public static String IP_COUNTRY_COLUMN      = "country";
    public static String IP_COUNTRY_CODE_COLUMN = "countryCode";
    public static String IP_ISP_COLUMN          = "isp";
    public static String IP_LAT_COLUMN          = "lat";
    public static String IP_LON_COLUMN          = "lon";
    public static String IP_ORG_COLUMN          = "org";
    public static String IP_IP_COLUMN           = "ip";
    public static String IP_REGION_COLUMN       = "region";
    public static String IP_REGION_NAME_COLUMN  = "regionName";
    public static String IP_ZIP_COLUMN          = "zip";
    public static String IP_DATE_COLUMN         = "dateSearched";

    public static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

}
