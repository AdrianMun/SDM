package databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import extras.Quotation;

/**
 * Created by adrymc96 on 18/02/18.
 */

public class SQL extends android.database.sqlite.SQLiteOpenHelper {

    private static SQL SQL = null;

    public static SQL getInstance(Context context){
        if(SQL == null){
            SQL = new SQL(context);
        }
        return SQL;
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE quotation_table (id INTEGER PRIMARY KEY AUTOINCREMENT, quote TEXT NOT NULL, author TEXT, UNIQUE(quote));");
    }

    public void onUpgrade(SQLiteDatabase db, int old_v, int new_v){

    }

    private SQL(Context context){
        super(context, "quotation_database", null, 1);
    }

    public ArrayList<Quotation> getQuotations(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("quotation_table", new String[]{"quote", "author"}, null, null, null, null, null);
        ArrayList<Quotation> list = new ArrayList<Quotation>();
        while(cursor.moveToNext()){
            Quotation aux = new Quotation(cursor.getString(0), cursor.getString(1));
            list.add(aux);
        }
        db.close();
        cursor.close();
        return list;
    }

    public boolean isQuote(String quote){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("quotation_table", null, "quote=?", new String[]{quote}, null, null, null, null);
        boolean res = cursor.getCount() > 0;
        db.close();
        cursor.close();
        return res;
    }

    public void insertQuote(String quote, String author){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("quote", quote);
        value.put("author", author);
        db.insert("quotation_table", null, value);
        db.close();
    }

    public void remove(String quote){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("quotation_table", "quote=?", new String[]{quote});
        db.close();
    }

    public void removeAll(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("quotation_table", null, null);
        db.close();
    }
}
