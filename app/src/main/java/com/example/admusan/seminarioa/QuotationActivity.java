package com.example.admusan.seminarioa;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import databases.AbstractData;
import databases.SQL;
import extras.Quotation;
import tasks.MyAddAsyncTask;

public class QuotationActivity extends AppCompatActivity {

    private boolean set_add_visible = false;
    String BaseDeDatos;
    TextView tv1, tv2;
    MyAddAsyncTask task;

    Menu menu;

    @Override
    @SuppressLint("StringFormatInvalid")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        BaseDeDatos=prefs.getString("BaseDeDatos", "0");

        tv1 = findViewById(R.id.Quotation_quote);
        tv2 = findViewById(R.id.Quotation_author);

        if(savedInstanceState == null) {

            tv1.setText(getString(R.string.Quotation_init_text, prefs.getString("nombre_insertado", "Nameless one")));
        }
        else {
            tv1.setText(R.string.Quotation_sample_quotation);
            tv2.setText(R.string.Quotation_sample_author);
            set_add_visible = savedInstanceState.getBoolean("add_visible");
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        this.menu = menu;
        getMenuInflater().inflate(R.menu.action_bar_quotation,menu);
        if(set_add_visible){
            menu.findItem(R.id.Add_favourites).setVisible(true);
        }
        else menu.findItem(R.id.Add_favourites).setVisible(false);
        return true;
    }

    protected void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("add_visible", menu.findItem(R.id.Add_favourites).isVisible());
    }

    @SuppressLint("StringFormatInvalid")
    public boolean onOptionsItemSelected(MenuItem item){
        final TextView tv1 = findViewById(R.id.Quotation_quote);
        final TextView tv2 = findViewById(R.id.Quotation_author);
        switch (item.getItemId()){
            case R.id.Add_favourites:
                if(BaseDeDatos.equals("0"))SQL.getInstance(this).insertQuote(tv1.getText().toString(), tv2.getText().toString());
                else AbstractData.getInstance(this).daoInterface().insert(new Quotation(tv1.getText().toString(), tv2.getText().toString()));
                item.setVisible(false);
                return true;
            case R.id.New_quote: //Refresh
                //item.setVisible(false);
                if(isInternetAvailable()) {
                    task = new MyAddAsyncTask(this);
                    task.execute();
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

    public void change_state(){
        menu.setGroupVisible(0, false);
        ProgressBar pb = findViewById(R.id.Quotation_progress);
        pb.setVisibility(View.VISIBLE);
    }

    public void add(Quotation q){
        final String quote = q.getQuoteText();
        tv1.setText(quote);
        tv2.setText(q.getQuoteAuthor());
        ProgressBar pb = findViewById(R.id.Quotation_progress);
        pb.setVisibility(View.INVISIBLE);
        menu.setGroupVisible(0, true);
        MenuItem item = menu.findItem(R.id.Add_favourites);
        item.setVisible(false);
        if(BaseDeDatos.equals("0")) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (!SQL.getInstance(QuotationActivity.this).isQuote(quote)) {
                        set_add_visible = true;
                        supportInvalidateOptionsMenu();
                    }
                }
            }).start();
        }
        else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (AbstractData.getInstance(QuotationActivity.this).daoInterface().search(quote) == null) {
                        set_add_visible = true;
                        supportInvalidateOptionsMenu();
                    }
                }
            }).start();
        }
    }

    public boolean isInternetAvailable(){
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(task != null && task.getStatus() == AsyncTask.Status.RUNNING){

        }
    }
}
