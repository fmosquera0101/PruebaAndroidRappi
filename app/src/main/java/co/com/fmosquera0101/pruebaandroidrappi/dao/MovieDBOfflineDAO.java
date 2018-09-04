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

    @Query("SELECT * FROM MovieDBOffline where isTopRated=:isTopRated")
    LiveData<List<MovieDBOffline>> getAllTopRatedMoviesOffline(String isTopRated);

    @Query("SELECT * FROM MovieDBOffline where isPopular=:isPopular")
    LiveData<List<MovieDBOffline>> getAllPopularMoviesOffline(String isPopular);

    @Query("SELECT * FROM MovieDBOffline where isUpcomming=:isUpcomming")
    LiveData<List<MovieDBOffline>> getAllUpComingMoviesOffline(String isUpcomming);

    @Query("SELECT * FROM MovieDBOffline where id=:id")
    LiveData<MovieDBOffline> getByIdMoviesOffline(String id);

    @Query("UPDATE MovieDBOffline SET genres = :genres, duration=:duration where id=:id")
    void updateByIdMovieDBOffline(String genres, String duration, String id);

}
