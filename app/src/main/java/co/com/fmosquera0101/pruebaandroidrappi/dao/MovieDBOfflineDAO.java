package co.com.fmosquera0101.pruebaandroidrappi.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import co.com.fmosquera0101.pruebaandroidrappi.model.MovieDBOffline;

@Dao
public interface MovieDBOfflineDAO {
    @Insert
    void insert(MovieDBOffline movieDBOffline);

    @Query("DELETE FROM MovieDBOffline")
    void deleteAll();

    @Query("SELECT * FROM MovieDBOffline")
    LiveData<List<MovieDBOffline>> getAllMoviesOffline();

}
