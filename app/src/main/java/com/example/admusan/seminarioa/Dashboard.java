package com.example.admusan.seminarioa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void dashboard_callback(View v){
        Intent intent = null;
        switch (v.getId()){
            case R.id.dashboard_boton1:
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
