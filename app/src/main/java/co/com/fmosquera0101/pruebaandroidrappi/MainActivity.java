package co.com.fmosquera0101.pruebaandroidrappi;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import co.com.fmosquera0101.pruebaandroidrappi.ViewModel.MovideDBViewModel;
import co.com.fmosquera0101.pruebaandroidrappi.model.Configuration;
import co.com.fmosquera0101.pruebaandroidrappi.model.Genre;
import co.com.fmosquera0101.pruebaandroidrappi.model.Images;
import co.com.fmosquera0101.pruebaandroidrappi.model.Movie;
import co.com.fmosquera0101.pruebaandroidrappi.model.MovieDBOffline;
import co.com.fmosquera0101.pruebaandroidrappi.model.Movies;
import co.com.fmosquera0101.pruebaandroidrappi.services.MovieDBDataServices;
import co.com.fmosquera0101.pruebaandroidrappi.services.RetrofitClienInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;
    private Context context;
    private ProgressBar progressBar;
    private Images images;
    private List<Movie>  movies;
    private Spinner spinner;
    private TextView textViewErroNetWork;
    private MovieDBDataServices movieDBDataServices;


    private MovideDBViewModel movideDBViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.RecyclerView_activity_main);
        spinner = (Spinner) findViewById(R.id.spinner_nav);
        textViewErroNetWork = (TextView) findViewById(R.id.text_view_not_network_conn_activity_main);

        spinner.setAdapter(getAdapter());
        movideDBViewModel = ViewModelProviders.of(this).get(MovideDBViewModel.class);

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        Boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();
        if(isConnected){
            movieDBDataServices = getMovieDBDataServices();
            Call<Movies> callMovies = movieDBDataServices.getPopularMovies();
            getMovies(callMovies);
            spinnerSetOnItemSelectedListener();

        }else{
            progressBar.setVisibility(View.INVISIBLE);
            textViewErroNetWork.setVisibility(View.VISIBLE);
            Toast.makeText(context, "No hay internet", Toast.LENGTH_SHORT).show();
            movideDBViewModel.getListMovieDBOffline().observe(this, new Observer<List<MovieDBOffline>>() {
                @Override
                public void onChanged(@Nullable List<MovieDBOffline> movieDBOfflines) {
                }
            });

        }






    }

    private void spinnerSetOnItemSelectedListener() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String filterSelect = (String)adapterView.getItemAtPosition(i);
                Call<Movies> callMovies = null;
                switch (filterSelect){
                    case"Popular":
                        callMovies = movieDBDataServices.getPopularMovies();
                        getMovies(callMovies);
                        break;
                    case "Top rated":
                        callMovies = movieDBDataServices.getTopRatedMovies();
                        getMovies(callMovies);
                        break;
                    case "Upcoming":
                        callMovies = movieDBDataServices.getUpcomingMovies();
                        getMovies(callMovies);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @NonNull
    private ArrayAdapter<CharSequence> getAdapter() {
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this, R.array.filter_movies, R.layout.support_simple_spinner_dropdown_item);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapterSpinner;
    }

    private void getMovies(Call<Movies> callMovies) {
        callMovies.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {

                movies = response.body().movies;
                movieAdapter = new MovieAdapter(movies, images,context);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(movieAdapter);
                progressBar.setVisibility(View.INVISIBLE);
                for (Movie movie: movies) {
                    MovieDBOffline movieDBOffline = new MovieDBOffline(
                            movie.id,
                            movie.originalLanguage,
                            movie.overview,
                            String.valueOf(movie.popularity),
                            movie.posterPath,
                            movie.releaseDate,
                            "test",
                            String.valueOf(movie.runtime)
                    );
                  //  movideDBViewModel.insert(movieDBOffline);
                }

            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                Toast.makeText(context, "Ocurrio un error", Toast.LENGTH_SHORT);
                progressBar.setVisibility(View.INVISIBLE);
                Log.d("Error_MainActivity", t.getMessage());


            }
        });


        Call<Configuration> call = movieDBDataServices.getConfiguration();
        call.enqueue(new Callback<Configuration>() {
            @Override
            public void onResponse(Call<Configuration> call, Response<Configuration> response) {
                if (response.isSuccessful()) {
                    images = response.body().images;
                    movieAdapter = new MovieAdapter(movies, images,context);
                    recyclerView.setAdapter(movieAdapter);
                    movieAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Configuration> call, Throwable t) {
            }
        });
    }

    private String getReleaseDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(Calendar.getInstance().getTime());
    }

    private MovieDBDataServices getMovieDBDataServices() {
        return RetrofitClienInstance.getRetrofitInstance().create(MovieDBDataServices.class);
    }
    private String getStringGenres(Movie movieFromId) {
        StringBuilder strbGenres = new StringBuilder();
        for (Genre genre:movieFromId.genres) {
            strbGenres.append(genre.name);
            strbGenres.append(", ");
        }
        String genres = strbGenres.toString().trim();
        return genres.substring(0, genres.length() - 1);
    }

}
