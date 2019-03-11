package com.tamirb.ipaddress;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.tamirb.ipaddress.Utils.Constants;
import com.tamirb.ipaddress.Utils.MyContextWrapper;


public class CustomAppCompat extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        SharedPreferences localSettings = PreferenceManager.getDefaultSharedPreferences(newBase.getApplicationContext());
        String locale = localSettings.getString (Constants.LOCALE, "");
        super.attachBaseContext(MyContextWrapper.wrap(newBase,locale));
    }

}
