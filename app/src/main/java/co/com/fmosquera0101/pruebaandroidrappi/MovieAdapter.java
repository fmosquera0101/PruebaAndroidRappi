package co.com.fmosquera0101.pruebaandroidrappi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import co.com.fmosquera0101.pruebaandroidrappi.model.Images;
import co.com.fmosquera0101.pruebaandroidrappi.model.Movie;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterHolder> {

    private List<Movie> movieList;
    private Images images;
    private Context context;
    public MovieAdapter(List<Movie> movieList, Images images, Context context){
        this.movieList = movieList;
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public MovieAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.movie_preview, parent, false);
        return new MovieAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterHolder holder, int position) {
        Movie movie = movieList.get(position);
        Glide.with(context).load(getImagePathUrl(movie)).apply(RequestOptions.centerCropTransform())
                .transition(withCrossFade())
                .into(holder.image_view_movie);
        holder.text_view_popularity.setText(String.valueOf(movie.popularity));
        holder.text_view_titile_movie.setText(movie.title);


    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MovieAdapterHolder extends RecyclerView.ViewHolder {
        public final View itemView;
        private ImageView image_view_movie;
        private TextView text_view_popularity;
        private TextView text_view_titile_movie;
        public MovieAdapterHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.image_view_movie = itemView.findViewById(R.id.image_view_movie);
            this.text_view_popularity = itemView.findViewById(R.id.text_view_popularity);
            this.text_view_titile_movie = itemView.findViewById(R.id.text_view_titile_movie);

        }
    }

    private String getImagePathUrl(Movie movie) {
        StringBuilder strbImagePathUrl = new StringBuilder("");
        if(null != images && !TextUtils.isEmpty(images.baseUrl)){
            strbImagePathUrl.append(images.baseUrl);
            if(null != images.posterSizes){
                strbImagePathUrl.append(images.posterSizes.size() > 4 ?images.posterSizes.get(4): "W500");

            }
        }
        if(!TextUtils.isEmpty(movie.posterPath)){
            strbImagePathUrl.append(movie.posterPath);
        }else{
            strbImagePathUrl.append(movie.backdropPath);
        }

        return strbImagePathUrl.toString();
    }
}
