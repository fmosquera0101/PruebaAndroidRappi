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
    public void insert(MovieDBOffline movieDBOffline){
        movieDBRepository.insert(movieDBOffline);
    }
}
