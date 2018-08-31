package co.com.fmosquera0101.pruebaandroidrappi.services;

import java.util.List;

import co.com.fmosquera0101.pruebaandroidrappi.model.Configuration;
import co.com.fmosquera0101.pruebaandroidrappi.model.Movie;
import co.com.fmosquera0101.pruebaandroidrappi.model.Movies;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDBDataServices {


    @GET("/3/discover/movie")
    Call<Movies> getMovies(@Query("primary_release_date.lte") String releaseDate,
                           @Query("sort_by") EnumSortBy.SortBy sortBy, @Query("page") int page);
    @GET("/3/movie/{id}")
    Call<Movie> getMovie(@Path("id") int id);
    @Headers("Cache-Control: public, max-stale=2419200")
    @GET("/3/configuration")
    Call<Configuration> getConfiguration();

    @GET("/3/discover/movie")
    Call<Movies> getMovies();
}
