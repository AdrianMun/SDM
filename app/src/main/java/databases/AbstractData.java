package databases;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import extras.Quotation;

/**
 * Created by Adrian on 19/02/2018.
 */
@Database(entities = {Quotation.class}, version = 1)
public abstract class AbstractData extends RoomDatabase {
    public abstract DaoInterface daoInterface();
    public static AbstractData Data = null;
    public synchronized static AbstractData getInstance(Context context){

        if(Data == null){
           Data = Room.databaseBuilder(context,AbstractData.class,"quotation_database").build();
        }
        return Data;
    };

}
