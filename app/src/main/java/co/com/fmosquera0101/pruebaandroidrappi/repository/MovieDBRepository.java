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

    public LiveData<List<MovieDBOffline>> getListAllTopRatedMovies(String isTopRated) {
        return movieDBOfflineDAO.getAllTopRatedMoviesOffline(isTopRated);
    }
    public LiveData<List<MovieDBOffline>> getListAllPopularMovies(String isPopular) {
        return movieDBOfflineDAO.getAllPopularMoviesOffline(isPopular);
    }
    public LiveData<List<MovieDBOffline>> getListAllUpcomingMovies(String isUpcoming) {
        return movieDBOfflineDAO.getAllUpComingMoviesOffline(isUpcoming);
    }
    public LiveData<MovieDBOffline> getByIdMoviesOffline(String id){
        return movieDBOfflineDAO.getByIdMoviesOffline(id);
    }
    public  void updateByIdMovieDBOffline(String genres, String duration, String id){
        movieDBOfflineDAO.updateByIdMovieDBOffline(genres, duration, id);
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
