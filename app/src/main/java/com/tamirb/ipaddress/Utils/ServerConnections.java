package com.tamirb.ipaddress.Utils;


import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tamirb.ipaddress.Model.IpInfo;
import com.tamirb.ipaddress.MainActivity;
import com.tamirb.ipaddress.R;


public class ServerConnections {

    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private String dateResponse = "";


    private static MainActivity mainActivity;

    // Get ip info
    static public void getIPInfo(String ip, MainActivity activity){
        mainActivity = activity;
        StringRequest stringRequest = new StringRequest(Constants.IP_API + ip,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        IpInfo ipInfo = IpInfoParseJSON.parseJSON(response);
                        if(ipInfo != null) {
                            mainActivity.ipInfoData(ipInfo);
                        } else{
                            mainActivity.bar.showProgress(mainActivity,false,mainActivity.mProgressView,mainActivity.mFormView);
                            Toast.makeText(mainActivity.getApplicationContext(),R.string.data_error,Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Notice network error
                        mainActivity.bar.showProgress(mainActivity,false,mainActivity.mProgressView,mainActivity.mFormView);
                        Toast.makeText(mainActivity.getApplicationContext(),R.string.network_error,Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(mainActivity.getApplicationContext());
        requestQueue.getCache().clear();
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

}
