package com.tamirb.ipaddress.Utils;

import com.tamirb.ipaddress.Model.IpInfo;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;



public class IpInfoParseJSON {

    // Get data from Json to strings
    static IpInfo parseJSON(String json){
        IpInfo ipInfo = null;
        if(StringUtils.isEmpty(json)){
            return ipInfo;
        }
        try {
            JSONObject jo = new JSONObject(json);
            ipInfo = new IpInfo();
            ipInfo.setCity(jo.getString("city"));
            ipInfo.setCountry(jo.getString("country"));
            ipInfo.setCountryCode(jo.getString("countryCode"));
            ipInfo.setIsp(jo.getString("isp"));
            ipInfo.setLat(jo.getDouble("lat"));
            ipInfo.setLon(jo.getDouble("lon"));
            ipInfo.setOrg(jo.getString("org"));
            ipInfo.setIp(jo.getString("query"));
            ipInfo.setRegion(jo.getString("region"));
            ipInfo.setRegionName(jo.getString("regionName"));
            ipInfo.setZip(jo.getString("zip"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return ipInfo;
    }

    // Get data from Json to strings
    static IpInfo parseJSON2(String json){
        IpInfo ipInfo = null;
        if(StringUtils.isEmpty(json)){
            return ipInfo;
        }
        try {
            JSONObject jo = new JSONObject(json);
            ipInfo = new IpInfo();
            ipInfo.setCity(jo.getString("city"));
            ipInfo.setCountry(jo.getString("country"));
            ipInfo.setCountryCode(jo.getString("countryCode"));
            ipInfo.setIsp(jo.getString("isp"));
            ipInfo.setLat(jo.getDouble("lat"));
            ipInfo.setLon(jo.getDouble("lon"));
            ipInfo.setOrg(jo.getString("org"));
            ipInfo.setIp(jo.getString("query"));
            ipInfo.setRegion("");
            ipInfo.setRegionName(jo.getString("region"));
            ipInfo.setZip("");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return ipInfo;
    }

}