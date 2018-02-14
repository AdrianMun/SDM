package com.example.admusan.seminarioa;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class QuotationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);
        TextView tv = findViewById(R.id.Quotation_quote);
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        tv.setText(getString(R.string.Quotation_init_text,prefs.getString("nombre_insertado","Nameless one")));
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.action_bar_quotation,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.Add_favourites:
                return true;
            case R.id.New_quote:
                TextView tv1 = findViewById(R.id.Quotation_quote);
                TextView tv2 = findViewById(R.id.Quotation_author);
                tv1.setText(R.string.Quotation_sample_quotation);
                tv2.setText(R.string.Quotation_sample_author);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void refresh_button_callback(View v){
        TextView tv1 = findViewById(R.id.Quotation_quote);
        TextView tv2 = findViewById(R.id.Quotation_author);

        tv1.setText(R.string.Quotation_sample_quotation);
        tv2.setText(R.string.Quotation_sample_author);
    }
}
