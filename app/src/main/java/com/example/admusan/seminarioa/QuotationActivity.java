package com.example.admusan.seminarioa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class QuotationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);
        TextView tv = findViewById(R.id.Quotation_quote);
        tv.setText(getString(R.string.Quotation_init_text,getResources().getString(R.string.nombre_usuario)));
    }

    void refresh_button_callback(View v){
        TextView tv1 = findViewById(R.id.Quotation_quote);
        TextView tv2 = findViewById(R.id.Quotation_author);

        tv1.setText(R.string.Quotation_sample_quotation);
        tv2.setText(R.string.Quotation_sample_author);
    }
}
