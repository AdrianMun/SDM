package databases;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

import extras.Quotation;

/**
 * Created by Adrian on 19/02/2018.
 */
@Dao
public interface DaoInterface {
@Insert
    public void insert(Quotation q);
@Delete
    public void delete(Quotation q);

@Query("SELECT * FROM quotation_table")
public List<Quotation> getList();
@Query("SELECT * FROM quotation_table WHERE quote =:s")
public Quotation search (String s);
@Query("DELETE FROM quotation_table")
public void deleteAll();
}
