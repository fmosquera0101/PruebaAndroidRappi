package co.com.fmosquera0101.pruebaandroidrappi;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.com.fmosquera0101.pruebaandroidrappi.ViewModel.MovideDBViewModel;
import co.com.fmosquera0101.pruebaandroidrappi.model.Genre;
import co.com.fmosquera0101.pruebaandroidrappi.model.Movie;
import co.com.fmosquera0101.pruebaandroidrappi.model.MovieDBOffline;
import co.com.fmosquera0101.pruebaandroidrappi.services.MovieDBDataServices;
import co.com.fmosquera0101.pruebaandroidrappi.services.RetrofitClienInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieDetail extends AppCompatActivity {

    private ImageView imageViewMovie;
    private TextView textViewTitleMovie;
    private TextView textViewReleaseDate;
    private TextView textViewOriginalLang;
    private TextView textViewGenres;
    private TextView textViewDuration;
    private TextView textViewOverview;
    private Intent intent;
    private Movie movie;
    private Context context;

    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private  Boolean isConnected;
    private MovideDBViewModel movideDBViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        intent = getIntent();
        context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        movie = (Movie) intent.getSerializableExtra("Movie");
        imageViewMovie = findViewById(R.id.image_view_movie_activity_movie_detail);
        textViewOriginalLang = findViewById(R.id.text_view_originalLangu_activity_movie_detail);
        textViewReleaseDate = findViewById(R.id.text_view_releasedate_activity_movie_detail);
        textViewTitleMovie = findViewById(R.id.text_view_titile_movie_activity_movie_detail);
        textViewGenres = findViewById(R.id.text_view_genres_activity_movie_detail);

        textViewDuration = findViewById(R.id.text_view_duration_activity_movie_detail);
        textViewOverview = findViewById(R.id.text_view_overview_activity_movie_detail);

        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

        movideDBViewModel = ViewModelProviders.of(this).get(MovideDBViewModel.class);
        MovieDBDataServices movieDBDataServices = getMovieDBDataServices();
        if(isConnected) {

            final Call<Movie> callMovie = movieDBDataServices.getMovie(Integer.parseInt(movie.id));
            callMovie.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    final Movie movieFromId = response.body();
                    setViews(movieFromId);
                    //new UpdateByIdMovieDBOffline().execute(movieFromId);

                    movideDBViewModel.getByIdMoviesOffline(movie.id).observe((LifecycleOwner) context, new Observer<MovieDBOffline>() {
                        @Override
                        public void onChanged(@Nullable MovieDBOffline movieDBOffline) {
                            if(null == movieDBOffline) {
                                movieDBOffline = new MovieDBOffline(0,
                                        movieFromId.id,
                                        movieFromId.title,
                                        movieFromId.originalLanguage,
                                        movieFromId.overview,
                                        String.valueOf(movieFromId.popularity),
                                        movieFromId.posterPath,
                                        movieFromId.releaseDate,
                                        getStringGenres(movieFromId.genres),
                                        String.valueOf(movieFromId.runtime),
                                        movie.isTopRated,
                                        movie.isPopular,
                                        movie.isUpcomming
                                );
                                movideDBViewModel.insert(movieDBOffline);
                            }else {
                                new UpdateByIdMovieDBOffline().execute(movieFromId);
                            }
                        }
                    });

                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                    Toast.makeText(context, "Ocurrio un error", Toast.LENGTH_SHORT);
                    Log.d("Error_MovieDetail", t.getMessage());
                }
            });
        }else{
            movideDBViewModel.getByIdMoviesOffline(movie.id).observe(this, new Observer<MovieDBOffline>() {
                @Override
                public void onChanged(@Nullable MovieDBOffline movieDBOffline) {
                    if(null != movieDBOffline) {
                        Movie movie = new Movie();
                        movie.id = movieDBOffline.getId();
                        movie.title = movieDBOffline.getTitle();
                        movie.originalLanguage = movieDBOffline.getOriginalLanguage();
                        movie.overview = movieDBOffline.getOverview();
                        movie.popularity = Float.parseFloat(movieDBOffline.getPopularity());
                        movie.posterPath = movieDBOffline.getPosterPath();
                        movie.releaseDate = movieDBOffline.getReleaseDate();

                        movie.genres = getGeneresOffilne(movieDBOffline.getGenres());
                        movie.runtime = Integer.parseInt(movieDBOffline.getDuration());
                        setViews(movie);
                    }
                }
            });
        }


    }

    private void setViews(Movie movieFromId) {
        movieFromId.posterPath = movie.posterPath;
        Glide.with(context).load(movieFromId.posterPath).diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageViewMovie);
        textViewTitleMovie.setText(movieFromId.title);
        textViewReleaseDate.setText(movieFromId.releaseDate);
        textViewOriginalLang.setText(movieFromId.originalLanguage);
        textViewDuration.setText(movieFromId.runtime <= 0 ? "no info..." : movieFromId.runtime + " Minutes");
        textViewOverview.setText(movieFromId.overview);
        List<Genre> listGenres = movieFromId.genres;
        if(null != listGenres && listGenres.size() > 0){
             textViewGenres.setText(getStringGenres(listGenres));
        }else{
            textViewGenres.setText("no info...");
        }

    }

    @NonNull
    private String getStringGenres(List<Genre> listGenres ) {
        StringBuilder strbGenres = new StringBuilder("");
        for (Genre genre:listGenres) {
            strbGenres.append(genre.name);
            strbGenres.append(", ");
        }

        String genres = strbGenres.toString().trim();
        if (genres.length() > 1){
            return genres.substring(0, genres.length() - 1);
        }
        return genres;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int item = menuItem.getItemId();
        if (item == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private MovieDBDataServices getMovieDBDataServices() {
        return RetrofitClienInstance.getRetrofitInstance().create(MovieDBDataServices.class);
    }

    private   List<Genre> getGeneresOffilne(String strGenres){
        List<Genre> listGenres = null;
        if (!TextUtils.isEmpty(strGenres)) {
            listGenres = new ArrayList<Genre>();
            List<String> listStringGenres = Arrays.asList(strGenres.split(","));
            for (String strGenre : listStringGenres) {
                Genre genre = new Genre();
                genre.name = strGenre;
                listGenres.add(genre);
            }
        }
        return listGenres;

    }

    private class UpdateByIdMovieDBOffline extends AsyncTask<Movie, Void, Void>{

        @Override
        protected Void doInBackground(Movie... movies) {
            Movie movieFromId = movies[0];
            movideDBViewModel.updateByIdMovieDBOffline(getStringGenres(movieFromId.genres), String.valueOf(movieFromId.runtime),
                    movieFromId.id);
            return null;
        }
    }
}
