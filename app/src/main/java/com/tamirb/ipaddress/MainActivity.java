package com.tamirb.ipaddress;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tamirb.ipaddress.Model.IpInfo;
import com.tamirb.ipaddress.Utils.AdViewLoad;
import com.tamirb.ipaddress.Utils.ProgressBar;
import com.tamirb.ipaddress.Utils.ServerConnections;

import org.apache.commons.lang3.StringUtils;

public class MainActivity extends CustomAppCompat implements OnMapReadyCallback, View.OnKeyListener {

    private LinearLayout bg;
    private LinearLayout ipInfoTable;
    private LinearLayout myIpInfo;
    private LinearLayout searchTable;
    private EditText searchIp;

    private TextView city;
    private TextView country;
    private TextView isp;
    private TextView coord;
    private TextView org;
    private TextView ip;
    private TextView regionName;
    private TextView regionCode;
    private TextView zip;
    private MapView mapView;
    private GoogleMap gmap;
    private Marker ipMarker;
    private AppCompatDialog dialog;
    private ImageView countryFlag;

    public ProgressBar bar;
    public View mProgressView;
    public View mFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bg = findViewById(R.id.mainBg);
        ipInfoTable = findViewById(R.id.ipInfoTable);
        myIpInfo = findViewById(R.id.myIpInfo);
        searchIp = findViewById(R.id.editTextSearchIp);
        searchTable = findViewById(R.id.searchTable);

        city        = findViewById(R.id.textViewCityData);
        country     = findViewById(R.id.textViewCountryData);
        isp         = findViewById(R.id.textViewISPData);
        coord       = findViewById(R.id.textViewCoordData);
        org         = findViewById(R.id.textViewOrgData);
        ip          = findViewById(R.id.textViewIpData);
        regionName = findViewById(R.id.textViewRegionData);
        regionCode  = findViewById(R.id.textViewRegionCodeData);
        zip         = findViewById(R.id.textViewZipData);
        countryFlag = findViewById(R.id.imageViewCountryFlag);

        mProgressView = findViewById(R.id.progressBar);
        mFormView = findViewById(R.id.mainLayout);
        bar = new ProgressBar();

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(null);
        mapView.getMapAsync(this);

        //AdMob initialize
        new AdViewLoad().start(this);
    }

    public void getIpInfo(View view){
        String search = searchIp.getText().toString();
        if(StringUtils.isEmpty(search)){
            searchTable.setBackgroundResource(R.drawable.forms_bad_bg);
        } else{
            bar.showProgress(this,true,mProgressView,mFormView);
            searchTable.setBackgroundResource(R.drawable.forms_good_bg);
            ServerConnections.getIPInfo(searchIp.getText().toString(),this);
        }
    }

    public void getMyIpInfo(View view){
        bar.showProgress(this,true,mProgressView,mFormView);
        searchTable.setBackgroundResource(R.drawable.forms_good_bg);
        ServerConnections.getIPInfo("",this);
    }

    public void backToMain(View view){
        bg.setBackgroundResource(R.drawable.bg_color);
        searchTable.setBackgroundResource(R.drawable.forms_bg);
        ipInfoTable.setVisibility(View.GONE);
        myIpInfo.setVisibility(View.VISIBLE);
    }

    public void ipInfoData(IpInfo ipInfo){
        bg.setBackgroundColor(getResources().getColor(R.color.bg_search));
        ipInfoTable.setVisibility(View.VISIBLE);
        myIpInfo.setVisibility(View.GONE);

        city.setText(ipInfo.getCity());
        country.setText(ipInfo.getCountry());
        isp.setText(ipInfo.getIsp());
        coord.setText(ipInfo.getLat()+" , "+ipInfo.getLon());
        org.setText(ipInfo.getOrg());
        ip.setText(ipInfo.getIp());
        regionName.setText(ipInfo.getRegionName());
        regionCode.setText(ipInfo.getRegion());
        zip.setText(ipInfo.getZip());
        countryFlag.setImageResource(getResources().getIdentifier(ipInfo.getCountryCode().toLowerCase() , "drawable", getPackageName()));
        LatLng ny = new LatLng(ipInfo.getLat(),ipInfo.getLon());
        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));
        MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.self_marker))
                .position(new LatLng(ipInfo.getLat(),ipInfo.getLon()));
        if(ipMarker != null){
            ipMarker.remove();
        }
        ipMarker = gmap.addMarker(markerOptions);
        gmap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                dialog = new AppCompatDialog(MainActivity.this,R.style.FullHeightDialog);
                dialog.setContentView(R.layout.maps_popup);
                dialog.setTitle(getString(R.string.maps));

                ImageView imageViewClose = (ImageView) dialog.findViewById(R.id.imageViewClose);
                imageViewClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
                return false;
            }
        });

        bar.showProgress(this,false,mProgressView,mFormView);
    }

    public void openMapsLocation(View view){
        dialog.dismiss();
        Uri.Builder directionsBuilder = new Uri.Builder()
                .scheme("https")
                .authority("www.google.com")
                .appendPath("maps")
                .appendPath("dir")
                .appendPath("")
                .appendQueryParameter("api", "1")
                .appendQueryParameter("destination", coord.getText().toString());
        Intent intent = this.getPackageManager().getLaunchIntentForPackage("com.google.android.apps.maps");
        if (intent != null) {
            startActivity(new Intent(Intent.ACTION_VIEW, directionsBuilder.build()));
        } else {
            // App not installed
            Toast.makeText(this,getString(R.string.not_installed),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER)) {
            getIpInfo(v);
            return true;

        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }
    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMinZoomPreference(10);
    }
}
