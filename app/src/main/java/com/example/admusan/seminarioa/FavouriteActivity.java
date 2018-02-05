package com.example.admusan.seminarioa;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FavouriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
    }
    protected void author_callback(View view){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://en.wikipedia.org/wiki/Special:Search?search="+"Albert Einstein"));
        if (intent.resolveActivity(getPackageManager()) !=null) {
            startActivity(intent);
        }
        }


    }

