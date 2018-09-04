package co.com.fmosquera0101.pruebaandroidrappi;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import co.com.fmosquera0101.pruebaandroidrappi.ViewModel.MovideDBViewModel;
import co.com.fmosquera0101.pruebaandroidrappi.model.Configuration;
import co.com.fmosquera0101.pruebaandroidrappi.model.Images;
import co.com.fmosquera0101.pruebaandroidrappi.model.Movie;
import co.com.fmosquera0101.pruebaandroidrappi.model.MovieDBOffline;
import co.com.fmosquera0101.pruebaandroidrappi.model.Movies;
import co.com.fmosquera0101.pruebaandroidrappi.services.EnumSortBy;
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
    private static final String TOP_RATED = "Top rated";
    private static final String POPULAR= "Popular";
    private static final String UPCOMING = "Upcoming";

    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private  Boolean isConnected;
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
        movieDBDataServices = getMovieDBDataServices();
        spinnerSetOnItemSelectedListener();

        Call<Movies> callMovies = movieDBDataServices.getMovies(getReleaseDate(), EnumSortBy.SortBy.RELEASE_DATE_DESCENDING);
        getMovies(callMovies, "", POPULAR, "");

    }


    @NonNull
    private List<Movie> getMovieListOffLine(@NonNull List<MovieDBOffline> movieDBOfflines) {
        List<Movie> listMovie = new ArrayList<Movie>();
        for (MovieDBOffline movieDBOffline: movieDBOfflines){
            Movie movie = new Movie();
            movie.id = movieDBOffline.getId();
            movie.title = movieDBOffline.getTitle();
            movie.originalLanguage =movieDBOffline.getOriginalLanguage();
            movie.overview = movieDBOffline.getOverview();
            movie.popularity = Float.parseFloat(movieDBOffline.getPopularity());
            movie.posterPath = movieDBOffline.getPosterPath();
            //movie.genres = movieDBOffline.getGenres();
            movie.runtime = Integer.parseInt(movieDBOffline.getDuration());
            listMovie.add(movie);

        }
        return listMovie;
    }

    private void spinnerSetOnItemSelectedListener() {

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                networkInfo = connectivityManager.getActiveNetworkInfo();
                isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

                String filterSelect = (String)adapterView.getItemAtPosition(i);
                Call<Movies> callMovies = null;
                switch (filterSelect){
                    case POPULAR:
                        if (isConnected) {
                            callMovies = movieDBDataServices.getPopularMovies();
                            getMovies(callMovies, "", POPULAR, "");
                        }else{
                            movideDBViewModel.getListAllPopularMovies(POPULAR).observe((LifecycleOwner) context, new Observer<List<MovieDBOffline>>() {
                                @Override
                                public void onChanged(@Nullable List<MovieDBOffline> movieDBOfflines) {

                                    if(null != movieDBOfflines && movieDBOfflines.size() > 0){
                                        List<Movie> listMovie = getMovieListOffLine(movieDBOfflines);
                                        setAdapterRecyclerView(listMovie);
                                    }else {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        textViewErroNetWork.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                        }
                        break;
                    case TOP_RATED:
                        if (isConnected) {
                            callMovies = movieDBDataServices.getTopRatedMovies();
                            getMovies(callMovies, TOP_RATED, "", "");
                        }else{
                            movideDBViewModel.getListAllTopRatedMovies(TOP_RATED).observe((LifecycleOwner) context, new Observer<List<MovieDBOffline>>() {
                                @Override
                                public void onChanged(@Nullable List<MovieDBOffline> movieDBOfflines) {

                                    if(null != movieDBOfflines && movieDBOfflines.size() > 0){
                                        List<Movie> listMovie = getMovieListOffLine(movieDBOfflines);
                                        setAdapterRecyclerView(listMovie);
                                    }else {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        textViewErroNetWork.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                        }
                        break;
                    case UPCOMING:
                        if (isConnected) {
                            callMovies = movieDBDataServices.getUpcomingMovies();
                            getMovies(callMovies, "", "", UPCOMING);
                        }else{
                            movideDBViewModel.getListAllUpcomingMovies(UPCOMING).observe((LifecycleOwner) context, new Observer<List<MovieDBOffline>>() {
                                @Override
                                public void onChanged(@Nullable List<MovieDBOffline> movieDBOfflines) {

                                    if(null != movieDBOfflines && movieDBOfflines.size() > 0){
                                        List<Movie> listMovie = getMovieListOffLine(movieDBOfflines);
                                        setAdapterRecyclerView(listMovie);
                                    }else {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        textViewErroNetWork.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                        }
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

    private void getMovies(Call<Movies> callMovies, final String isTopRated, final String isPopular, final String isUpcomming) {
        callMovies.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {

                movies = new ArrayList<Movie>();
                for (Movie movie: response.body().movies) {
                    movie.isPopular = isPopular;
                    movie.isTopRated = isTopRated;
                    movie.isUpcomming = isUpcomming;
                    movies.add(movie);
                }
                setAdapterRecyclerView(movies);
                progressBar.setVisibility(View.INVISIBLE);


                for (Movie movie: movies) {
                    MovieDBOffline movieDBOffline = new MovieDBOffline(0,
                            movie.id,
                            movie.title,
                            movie.originalLanguage,
                            movie.overview,
                            String.valueOf(movie.popularity),
                            movie.posterPath,
                            movie.releaseDate,
                            "",
                            String.valueOf(movie.runtime),
                            isTopRated,
                            isPopular,
                            isUpcomming
                    );
                    movideDBViewModel.insert(movieDBOffline);
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

    private void setAdapterRecyclerView(List<Movie> movies) {
        movieAdapter = new MovieAdapter(movies, images,context);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();
    }

    private MovieDBDataServices getMovieDBDataServices() {
        return RetrofitClienInstance.getRetrofitInstance().create(MovieDBDataServices.class);
    }
    private String getReleaseDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(Calendar.getInstance().getTime());
    }

}
