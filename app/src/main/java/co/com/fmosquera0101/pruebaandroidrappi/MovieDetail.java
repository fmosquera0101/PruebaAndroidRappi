package co.com.fmosquera0101.pruebaandroidrappi;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import co.com.fmosquera0101.pruebaandroidrappi.model.Genre;
import co.com.fmosquera0101.pruebaandroidrappi.model.Movie;
import co.com.fmosquera0101.pruebaandroidrappi.model.Movies;
import co.com.fmosquera0101.pruebaandroidrappi.model.SpokenLanguages;
import co.com.fmosquera0101.pruebaandroidrappi.services.EnumSortBy;
import co.com.fmosquera0101.pruebaandroidrappi.services.MovieDBDataServices;
import co.com.fmosquera0101.pruebaandroidrappi.services.RetrofitClienInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MovieDetail extends AppCompatActivity {

    ImageView imageViewMovie;
    TextView textViewTitleMovie;
    TextView textViewReleaseDate;
    TextView textViewOriginalLang;
    TextView textViewGenres;
    TextView textViewDuration;
    TextView textViewOverview;
    private Intent intent;
    private Movie movie;
    private Context context;
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


        MovieDBDataServices movieDBDataServices = getMovieDBDataServices();
        final Call<Movie> callMovie = movieDBDataServices.getMovie(Integer.parseInt(movie.id));
        callMovie.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie movieFromId =  response.body();
                movieFromId.posterPath = movie.posterPath;
                Glide.with(context).load(movieFromId.posterPath).apply(RequestOptions.centerCropTransform())
                        .transition(withCrossFade())
                        .into(imageViewMovie);
                textViewTitleMovie.setText(movieFromId.title);
                textViewReleaseDate.setText(movieFromId.releaseDate);
                textViewOriginalLang.setText(movieFromId.originalLanguage);
                textViewDuration.setText(movieFromId.runtime <= 0 ? "":movieFromId.runtime +" Minutes");
                textViewOverview.setText(movieFromId.overview);
                textViewGenres.setText(getStringGenres(movieFromId));



            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(context, "Ocurrio un error", Toast.LENGTH_SHORT);
                Log.d("Error_MovieDetail", t.getMessage());
            }
        });


    }

    @NonNull
    private String getStringGenres(Movie movieFromId) {
        StringBuilder strbGenres = new StringBuilder();
        for (Genre genre:movieFromId.genres) {
            strbGenres.append(genre.name);
            strbGenres.append(", ");
        }
        String genres = strbGenres.toString().trim();
        return genres.substring(0, genres.length() - 1);
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
}
