package com.tamirb.ipaddress.Utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.os.Build;

import java.util.Locale;

public class MyContextWrapper extends ContextWrapper {

    public MyContextWrapper(Context base) {
        super(base);
    }

    @SuppressWarnings("deprecation")
    public static ContextWrapper wrap(Context context, String language) {
        Configuration config = context.getResources().getConfiguration();
        Locale sysLocale = null;

        switch(language){
            case "en":
                sysLocale = new Locale("en");
                break;
            case "ru":
                sysLocale = new Locale("ru");
                break;
            case "he":
                sysLocale = new Locale("he");
                break;
            case "ar":
                sysLocale = new Locale("ar");
                break;
            default:
                sysLocale = new Locale("en");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    for(String localLang : Constants.LOCAL_LANGUAGE){
                        if(localLang.equals(Locale.getDefault().getDisplayLanguage())){
                            sysLocale = Locale.getDefault();
                            break;
                        }
                    }
                }
                break;
        }

        Locale.setDefault(sysLocale);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            setSystemLocale(config, sysLocale);
        } else {
            setSystemLocaleLegacy(config, sysLocale);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            context = context.createConfigurationContext(config);
        } else {
            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        }
        return new MyContextWrapper(context);
    }

    @SuppressWarnings("deprecation")
    public static Locale getSystemLocaleLegacy(Configuration config){
        return config.locale;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static Locale getSystemLocale(Configuration config){
        return config.getLocales().get(0);
    }

    @SuppressWarnings("deprecation")
    public static void setSystemLocaleLegacy(Configuration config, Locale locale){
        config.locale = locale;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static void setSystemLocale(Configuration config, Locale locale){
        config.setLocale(locale);
    }
}