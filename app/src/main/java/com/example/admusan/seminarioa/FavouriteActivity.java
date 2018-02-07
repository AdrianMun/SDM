package com.example.admusan.seminarioa;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import extras.Adaptador;
import extras.Quotation;

public class FavouriteActivity extends AppCompatActivity {

    ArrayList<Quotation> list;
    Adaptador adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        list = getMockQuotations();
        adaptador = new Adaptador(this, R.layout.quotation_list_row, list);
        ListView listView = findViewById(R.id.list_view_favourite);
        listView.setAdapter(adaptador);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                if(list.get(pos).getAutor().equals("")){
                    Toast.makeText(FavouriteActivity.this, R.string.no_existe, Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://en.wikipedia.org/wiki/Special:Search?search=" + list.get(pos).getAutor()));
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
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
                        item.setVisible(false);
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


    }

