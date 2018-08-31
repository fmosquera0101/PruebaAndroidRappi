package co.com.fmosquera0101.pruebaandroidrappi;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import co.com.fmosquera0101.pruebaandroidrappi.model.Configuration;
import co.com.fmosquera0101.pruebaandroidrappi.model.Images;
import co.com.fmosquera0101.pruebaandroidrappi.model.Movie;
import co.com.fmosquera0101.pruebaandroidrappi.model.Movies;
import co.com.fmosquera0101.pruebaandroidrappi.services.EnumSortBy;
import co.com.fmosquera0101.pruebaandroidrappi.services.MovieDBDataServices;
import co.com.fmosquera0101.pruebaandroidrappi.services.RetrofitClienInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    MovieAdapter movieAdapter;
    RecyclerView recyclerView;
    Context context;
    ProgressBar progressBar;
    Images images;
    List<Movie>  movies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.RecyclerView_activity_main);
        MovieDBDataServices movieDBDataServices = getMovieDBDataServices();
        Call<Movies> callMovies = movieDBDataServices.getMovies();
        callMovies.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {

                movies = response.body().movies;
                movieAdapter = new MovieAdapter(movies, images,context);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(movieAdapter);
                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                Toast.makeText(context, "Ocurrio un error", Toast.LENGTH_SHORT);
                progressBar.setVisibility(View.INVISIBLE);
                Log.d("Error", t.getMessage());


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
}
