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
    String cita="";
    @ColumnInfo (name="author")
    private String autor;
    public Quotation(){}
    public Quotation(String c, String a){
        cita = c;
        autor = a;
    }

    public String getCita(){
        return cita;
    }

    public String getAutor(){
        return autor;
    }

    public void setAutor(String a){
        autor = a;
    }

    public void setCita(String c){
        cita = c;
    }
}
