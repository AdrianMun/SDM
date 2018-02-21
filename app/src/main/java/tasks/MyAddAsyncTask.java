package tasks;

import android.os.AsyncTask;

import com.example.admusan.seminarioa.QuotationActivity;

import java.lang.ref.WeakReference;

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
        quotation = new Quotation("12", "34");
        if(ref.get() != null) ref.get().add(quotation);
    }
}
