package tasks;

import android.os.AsyncTask;
import android.view.MenuItem;

import com.example.admusan.seminarioa.FavouriteActivity;
import com.example.admusan.seminarioa.QuotationActivity;
import com.example.admusan.seminarioa.R;

import java.lang.ref.WeakReference;
import java.util.List;

import databases.AbstractData;
import databases.SQL;
import extras.Quotation;

/**
 * Created by admusan on 21/02/2018.
 */

public class MyAsyncTask extends AsyncTask<Boolean, Void, List<Quotation>> {

    WeakReference<FavouriteActivity> fav_act;

    public MyAsyncTask(FavouriteActivity fav){
        fav_act = new WeakReference<FavouriteActivity>(fav);
    }

    @Override
    protected List<Quotation> doInBackground(Boolean... booleans) {

        if(booleans[0]){
            if(fav_act.get()!=null) return SQL.getInstance(fav_act.get()).getQuotations();
            }
        else {
            if(fav_act.get()!=null) return AbstractData.getInstance(fav_act.get()).daoInterface().getList();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Quotation> quotations) {
        super.onPostExecute(quotations);
        if(fav_act.get() != null) fav_act.get().addQuote(quotations);
    }
}
