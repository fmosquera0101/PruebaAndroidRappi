package co.com.fmosquera0101.pruebaandroidrappi.dao;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import co.com.fmosquera0101.pruebaandroidrappi.model.MovieDBOffline;

@Database(entities = {MovieDBOffline.class}, version = 1)
public abstract class MovieDBRoomDatabase extends RoomDatabase {
    public abstract MovieDBOfflineDAO movieDBOfflineDAO();

    private static MovieDBRoomDatabase INSTANCE;

    public static MovieDBRoomDatabase getInstace(final Context context){
        if(null == INSTANCE){
            synchronized (MovieDBRoomDatabase.class){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        MovieDBRoomDatabase.class, "MovieDBOffline_database").addCallback(sRoomDatabaseCallback).build();
            }
        }
        return INSTANCE;
    }
    private static  RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>{
        private final MovieDBOfflineDAO movieDBOfflineDAO;
        public PopulateDbAsync(MovieDBRoomDatabase movieDBRoomDatabase){
            this.movieDBOfflineDAO = movieDBRoomDatabase.movieDBOfflineDAO();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            movieDBOfflineDAO.deleteAll();
            return null;
        }
    }
}
