package com.example.admusan.seminarioa;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import databases.SQL;

public class QuotationActivity extends AppCompatActivity {

    private int received_quotes = 0;
    private boolean set_add_visible = false;
    Menu menu;

    @Override
    @SuppressLint("StringFormatInvalid")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);
        TextView tv1 = findViewById(R.id.Quotation_quote);
        if(savedInstanceState == null) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            tv1.setText(getString(R.string.Quotation_init_text, prefs.getString("nombre_insertado", "Nameless one")));
        }
        else {
            TextView tv2 = findViewById(R.id.Quotation_author);
            received_quotes = savedInstanceState.getInt("received_quotes");
            tv1.setText(getString(R.string.Quotation_sample_quotation, received_quotes));
            tv2.setText(getString(R.string.Quotation_sample_author, received_quotes));
            set_add_visible = savedInstanceState.getBoolean("add_visible");
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        this.menu = menu;
        getMenuInflater().inflate(R.menu.action_bar_quotation,menu);
        if(set_add_visible){
            menu.findItem(R.id.Add_favourites).setVisible(true);
        }
        return true;
    }

    protected void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
        bundle.putInt("received_quotes", received_quotes);
        bundle.putBoolean("add_visible", menu.findItem(R.id.Add_favourites).isVisible());
    }

    @SuppressLint("StringFormatInvalid")
    public boolean onOptionsItemSelected(MenuItem item){
        TextView tv1 = findViewById(R.id.Quotation_quote);
        TextView tv2 = findViewById(R.id.Quotation_author);
        switch (item.getItemId()){
            case R.id.Add_favourites:
                item.setVisible(false);
                SQL.getInstance(this).insertQuote(tv1.getText().toString(), tv2.getText().toString());
                return true;
            case R.id.New_quote:
                received_quotes++;
                String quote = getString(R.string.Quotation_sample_quotation, received_quotes);
                tv1.setText(getString(R.string.Quotation_sample_quotation, received_quotes));
                tv2.setText(getString(R.string.Quotation_sample_author, received_quotes));
                if(!SQL.getInstance(this).isQuote(quote)) {
                    MenuItem menu_button = menu.findItem(R.id.Add_favourites);
                    menu_button.setVisible(true);
                }
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
