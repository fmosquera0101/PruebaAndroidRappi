package co.com.fmosquera0101.pruebaandroidrappi.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import co.com.fmosquera0101.pruebaandroidrappi.dao.MovieDBOfflineDAO;
import co.com.fmosquera0101.pruebaandroidrappi.dao.MovieDBRoomDatabase;
import co.com.fmosquera0101.pruebaandroidrappi.model.MovieDBOffline;

public class MovieDBRepository {
    private MovieDBOfflineDAO movieDBOfflineDAO;
    private LiveData<List<MovieDBOffline>> listMovieDBOffline;

    public MovieDBRepository(Application application){
        MovieDBRoomDatabase movieDBRoomDatabase = MovieDBRoomDatabase.getInstace(application);
        movieDBOfflineDAO = movieDBRoomDatabase.movieDBOfflineDAO();
        listMovieDBOffline = movieDBOfflineDAO.getAllMoviesOffline();
    }


    public LiveData<List<MovieDBOffline>> getListMovieDBOffline() {
        return listMovieDBOffline;
    }
    public void insert(MovieDBOffline movieDBOffline){
        new insertAsyncTask(movieDBOfflineDAO).execute(movieDBOffline);
    }
    private static class insertAsyncTask extends AsyncTask<MovieDBOffline, Void, Void>{
        private MovieDBOfflineDAO movieDBOfflineDAO;
        public insertAsyncTask(MovieDBOfflineDAO movieDBOfflineDAO){
            this.movieDBOfflineDAO = movieDBOfflineDAO;
        }
        @Override
        protected Void doInBackground(MovieDBOffline... movieDBOfflines) {
            movieDBOfflineDAO.insert(movieDBOfflines[0]);
            return null;
        }
    }
}
