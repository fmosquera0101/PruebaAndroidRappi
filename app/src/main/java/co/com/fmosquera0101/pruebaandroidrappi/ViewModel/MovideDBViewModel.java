package co.com.fmosquera0101.pruebaandroidrappi.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import co.com.fmosquera0101.pruebaandroidrappi.model.MovieDBOffline;
import co.com.fmosquera0101.pruebaandroidrappi.repository.MovieDBRepository;

public class MovideDBViewModel extends AndroidViewModel {
    private MovieDBRepository movieDBRepository;
    private LiveData<List<MovieDBOffline>> listMovieDBOffline;

    public MovideDBViewModel(@NonNull Application application) {
        super(application);
        movieDBRepository = new MovieDBRepository(application);
        listMovieDBOffline = movieDBRepository.getListMovieDBOffline();
    }

    public LiveData<List<MovieDBOffline>> getListMovieDBOffline() {
        return listMovieDBOffline;
    }
    public LiveData<List<MovieDBOffline>> getListAllTopRatedMovies(String isTopRated) {
        return movieDBRepository.getListAllTopRatedMovies(isTopRated);
    }
    public LiveData<List<MovieDBOffline>> getListAllPopularMovies(String isPopular) {
        return movieDBRepository.getListAllPopularMovies(isPopular);
    }
    public LiveData<List<MovieDBOffline>> getListAllUpcomingMovies(String isUpcoming) {
        return movieDBRepository.getListAllUpcomingMovies(isUpcoming);
    }
    public LiveData<MovieDBOffline> getByIdMoviesOffline(String id){
        return movieDBRepository.getByIdMoviesOffline(id);
    }
    public void updateByIdMovieDBOffline(String genres, String duration, String id){
        movieDBRepository.updateByIdMovieDBOffline(genres, duration, id);
    }
    public void insert(MovieDBOffline movieDBOffline){
        movieDBRepository.insert(movieDBOffline);
    }
}
