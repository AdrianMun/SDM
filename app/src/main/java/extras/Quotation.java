package extras;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by admarcar on 07/02/2018.
 */
@Entity(tableName = "quotation_table",  indices = {@Index(value = {"quote"}, unique = true)})

public class Quotation {
    @PrimaryKey(autoGenerate=true) public int id;


    @ColumnInfo (name="quote") @NonNull
    private String quoteText="";
    @ColumnInfo (name="author")
    private String quoteAuthor;
    public Quotation(){}
    public Quotation(String c, String a){
        quoteText = c;
        quoteAuthor = a;
    }

    public String getQuoteText(){
        return quoteText;
    }

    public String getQuoteAuthor(){
        return quoteAuthor;
    }

    public void setQuoteAuthor(String a){
        quoteAuthor = a;
    }

    public void setQuoteText(String c){
        quoteText = c;
    }
}
