package com.tamirb.ipaddress;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.tamirb.ipaddress.Utils.Constants;
import com.tamirb.ipaddress.Utils.MyContextWrapper;


public class CustomAppCompat extends AppCompatActivity {

    private AppCompatDialog menuDialog;

    @Override
    protected void attachBaseContext(Context newBase) {
        SharedPreferences localSettings = PreferenceManager.getDefaultSharedPreferences(newBase.getApplicationContext());
        String locale = localSettings.getString (Constants.LOCALE, "");
        super.attachBaseContext(MyContextWrapper.wrap(newBase,locale));
    }

    public void startMenu(View view){
        menuDialog = new AppCompatDialog(this,R.style.FullHeightDialog);
        menuDialog.setContentView(R.layout.menu_popup);
        menuDialog.setTitle(getString(R.string.maps));

        ImageView imageViewClose = (ImageView) menuDialog.findViewById(R.id.imageViewClose);
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuDialog.dismiss();
            }
        });

        menuDialog.show();
    }

    public void onClickDonate (View view){
        menuDialog.dismiss();
        Uri uriUrl = Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=5L7U52CT9T3DJ");
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    public void onClickShare (View view){
        menuDialog.dismiss();
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        String sAux = "Let me recommend you on this application:\n";
        sAux = sAux + "https://play.google.com/store/apps/details?id=com.tamirb.ipaddress";
        i.putExtra(Intent.EXTRA_TEXT, sAux);
        startActivity(Intent.createChooser(i, "choose one"));
    }

    public void onClickLanguage (View view){
        Toast.makeText(this,getString(R.string.soon),Toast.LENGTH_SHORT).show();
    }

    public void onClickHistory (View view){
        menuDialog.dismiss();
        if(!this.getClass().getSimpleName().equals(HistoryActivity.class.getSimpleName())){
            Intent history = new Intent(this, HistoryActivity.class);
            startActivityForResult(history,Constants.HISTORY_ACTIVITY);
        }

        //Toast.makeText(this,getString(R.string.soon),Toast.LENGTH_SHORT).show();
    }
}
