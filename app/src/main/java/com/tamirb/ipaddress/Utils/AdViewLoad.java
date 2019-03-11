package com.tamirb.ipaddress.Utils;

import android.app.Activity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import com.tamirb.ipaddress.R;

//AdMob initialize
public class AdViewLoad {

    public void start(Activity activity){
        AdView adView;
        adView = new AdView(activity);
        adView.setAdSize(AdSize.SMART_BANNER);
        MobileAds.initialize(activity,
                "ca-app-pub-1700840904778100/9894667478");
        adView = activity.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("55B17B637C71E08068AC4BF16D40C5DB").build();
        adView.loadAd(adRequest);
    }

}
