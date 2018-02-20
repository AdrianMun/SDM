package com.example.admusan.seminarioa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import databases.AbstractData;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if(prefs.getBoolean("first_run", true)) {
            AbstractData.getInstance(this).daoInterface().search("");
            prefs.edit().putBoolean("first_run",false);

        }
        }


    public void dashboard_callback(View v){
        Intent intent = null;
        switch (v.getId()){
            case R.id.dashboard_boton1:
                intent = new Intent(this, QuotationActivity.class);
                break;
            case R.id.dashboard_boton2:
                intent = new Intent(this, FavouriteActivity.class);
                break;
            case R.id.dashboard_boton3:
                intent = new Intent(this, SettingsActivity.class);
                break;
            case R.id.dashboard_boton4:
                intent = new Intent(this, AboutActivity.class);
                break;
        }
        startActivity(intent);
    }
}
