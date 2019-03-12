package com.tamirb.ipaddress;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tamirb.ipaddress.DB.DBHistory;
import com.tamirb.ipaddress.Model.IpInfo;
import com.tamirb.ipaddress.Utils.AdViewLoad;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryActivity extends CustomAppCompat {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private DBHistory dbHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        dbHistory = new DBHistory(this);
        List<IpInfo> ipInfoList = dbHistory.getAllIpSearches();
        ListView historyListView = (ListView)findViewById(R.id.usesListView);
        MyUsesAdapter useAdapter = new MyUsesAdapter(this,R.layout.single_ip_search,ipInfoList);
        historyListView.setAdapter(useAdapter);

        //AdMob initialize
        new AdViewLoad().start(this);
    }

    public void onClickLogo(View view) {
        finish();
    }

        //split uses info in uses list view
    class MyUsesAdapter extends ArrayAdapter<IpInfo>
    {

        public MyUsesAdapter(Context context, int resource, List<IpInfo> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View theView, ViewGroup parent) {
            final IpInfo ipInfo;
            try{
                if(theView == null) {
                    LayoutInflater li = LayoutInflater.from(getContext());
                    theView = li.inflate(R.layout.single_ip_search, null);
                }

                ipInfo = getItem(position);
                TextView ip = (TextView)theView.findViewById(R.id.ip);
                TextView show = (TextView)theView.findViewById(R.id.show);
                TextView date = (TextView)theView.findViewById(R.id.date);

                ip.setText(ipInfo.getIp());
                show.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent output = new Intent();
                        output.putExtra(MainActivity.ipAddress, ipInfo.getIp());
                        setResult(RESULT_OK, output);
                        finish();
                    }
                });
                date.setText(sdf.format(ipInfo.getDateSearched()));

            } catch (Exception e){
                e.printStackTrace();
            }

            return theView;
        }
    }
}
