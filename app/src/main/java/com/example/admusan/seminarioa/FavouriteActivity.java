package com.example.admusan.seminarioa;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import databases.AbstractData;
import databases.SQL;
import extras.Adaptador;
import extras.Quotation;
import tasks.MyAsyncTask;

public class FavouriteActivity extends AppCompatActivity {

    ArrayList<Quotation> list;
    Adaptador adaptador;
    String BaseDeDatos;
    Menu menu;
    boolean delete_visible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        BaseDeDatos=prefs.getString("BaseDeDatos", "0");


        this.list = new ArrayList<Quotation>();
        adaptador = new Adaptador(this, R.layout.quotation_list_row, list);
        ListView listView = findViewById(R.id.list_view_favourite);
        listView.setAdapter(adaptador);
        Boolean array[] = new Boolean[1];
        array[0] = new Boolean(prefs.getString("BaseDeDatos", "0").equals("0"));
        MyAsyncTask task = new MyAsyncTask(this);
        task.execute(array);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                if(list.get(pos).getQuoteAuthor().equals("")){
                    Toast.makeText(FavouriteActivity.this, R.string.no_existe, Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://en.wikipedia.org/wiki/Special:Search?search=" + list.get(pos).getQuoteAuthor()));
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int pos, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FavouriteActivity.this);
                builder.setMessage(R.string.message_builder);
                final Quotation to_delete = list.get(pos);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        new Thread(new Runnable(){
                            public void run(){

                                if(BaseDeDatos.equals("0"))SQL.getInstance(FavouriteActivity.this).remove(to_delete.getQuoteText());
                                else {
                                    AbstractData.getInstance(FavouriteActivity.this).daoInterface().delete(to_delete);
                                }
                                if(list.size() == 0){
                                    delete_visible = false;
                                    supportInvalidateOptionsMenu();
                                }
                            }
                        }).start();

                        list.remove(pos);
                        adaptador.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton(R.string.no, null);
                builder.create().show();
                return true;
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.action_bar_favourite,menu);
        if(delete_visible) menu.findItem(R.id.Delete).setVisible(true);
        return true;
    }

    public boolean onOptionsItemSelected(final MenuItem item){
        switch (item.getItemId()){
            case R.id.Delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(FavouriteActivity.this);
                builder.setMessage(R.string.message_builder);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        list.clear();
                        adaptador.notifyDataSetChanged();

                        new Thread(new Runnable(){
                            public void run(){

                                if(BaseDeDatos.equals("0")) SQL.getInstance(FavouriteActivity.this).removeAll();
                                else AbstractData.getInstance(FavouriteActivity.this).daoInterface().deleteAll();
                                delete_visible = false;
                                supportInvalidateOptionsMenu();
                            }
                        }).start();



                    }
                });
                builder.setNegativeButton(R.string.no, null);
                builder.create().show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public ArrayList<Quotation> getMockQuotations(){
        ArrayList<Quotation> list = new ArrayList<Quotation>();
        list.add(new Quotation("cita 1","Albert Einstein"));
        list.add(new Quotation("cita 2",""));
        list.add(new Quotation("cita 3","autor 3"));
        list.add(new Quotation("cita 4","autor 4"));
        list.add(new Quotation("cita 5","autor 5"));
        list.add(new Quotation("cita 6","autor 6"));
        list.add(new Quotation("cita 7","autor 7"));
        list.add(new Quotation("cita 8","autor 8"));
        list.add(new Quotation("cita 9","autor 9"));
        list.add(new Quotation("cita 10","autor 10"));
        return list;
    }


    public void addQuote(List<Quotation> list){

        this.list.addAll(list);
        adaptador.notifyDataSetChanged();
        if(this.list.size() > 0){
            delete_visible = true;
            supportInvalidateOptionsMenu();
        }
    }

    }

