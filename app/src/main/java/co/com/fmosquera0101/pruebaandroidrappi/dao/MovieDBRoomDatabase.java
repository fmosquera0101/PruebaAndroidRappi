package co.com.fmosquera0101.pruebaandroidrappi.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import co.com.fmosquera0101.pruebaandroidrappi.model.MovieDBOffline;

@Database(entities = {MovieDBOffline.class}, version = 1)
public abstract class MovieDBRoomDatabase extends RoomDatabase {
    public abstract MovieDBOfflineDAO movieDBOfflineDAO();

    private static MovieDBRoomDatabase INSTANCE;

    public static MovieDBRoomDatabase getInstace(final Context context){
        if(null == INSTANCE){
            synchronized (MovieDBRoomDatabase.class){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        MovieDBRoomDatabase.class, "MovieDBOffline_database").build();
            }
        }
        return INSTANCE;
    }
}
