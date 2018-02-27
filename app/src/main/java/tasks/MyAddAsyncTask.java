package tasks;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.example.admusan.seminarioa.QuotationActivity;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import extras.Quotation;

/**
 * Created by admusan on 21/02/2018.
 */

public class MyAddAsyncTask extends AsyncTask<Void, Void, Quotation> {

    WeakReference<QuotationActivity> ref;

    public MyAddAsyncTask(QuotationActivity act){
        ref = new WeakReference<QuotationActivity>(act);
    }

    @Override
    protected Quotation doInBackground(Void... voids) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http");
        builder.authority("api.forismatic.com");
        builder.appendPath("api");
        builder.appendPath("1.0");
        builder.appendPath(""); //Anyade el extra /


        if(ref.get() == null) return null;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ref.get());

        if(prefs.getString("http_insertado","0").equals("0")) {

        builder.appendQueryParameter("method","getQuote");
        builder.appendQueryParameter("format","json");

        if(prefs.getString("idioma_insertado","0").equals("0")) builder.appendQueryParameter("lang", "en");
        else builder.appendQueryParameter("lang", "ru");

            try {
                URL url = new URL(builder.build().toString());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                System.err.println(connection.getResponseCode() + " - " + builder.build().toString());
                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    Gson gson = new Gson();
                    Quotation q = gson.fromJson(reader, Quotation.class);
                    reader.close();
                    return q;
                }
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {

            String params="method=getQuote&format=json";
            if(prefs.getString("idioma_insertado","0").equals("0")) params=params.concat("&lang=en");
            else params=params.concat("&lang=ru");

            try {
                URL url = new URL(builder.build().toString());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(params);
                writer.flush();
                writer.close();
                System.err.println(connection.getResponseCode() + " - " + builder.build().toString());
                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    Gson gson = new Gson();
                    Quotation q = gson.fromJson(reader, Quotation.class);
                    reader.close();
                    return q;
                }
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(ref.get() != null) ref.get().change_state();
    }

    @Override
    protected void onPostExecute(Quotation quotation) {
        super.onPostExecute(quotation);
        if(ref.get() != null) ref.get().add(quotation);
    }
}
