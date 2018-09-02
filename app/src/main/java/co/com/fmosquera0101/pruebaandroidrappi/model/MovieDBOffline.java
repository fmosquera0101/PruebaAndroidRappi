package co.com.fmosquera0101.pruebaandroidrappi.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class MovieDBOffline {

    @PrimaryKey
    @NonNull
    private String id;
    private String originalLanguage;
    private String overview;
    private String popularity;
    private String posterPath;
    private String releaseDate;
    private String genres;
    private String duration;

    public MovieDBOffline(String id, String originalLanguage, String overview, String popularity,
                          String posterPath, String releaseDate, String genres, String duration){
        this.id = id;
        this.originalLanguage = originalLanguage;
        this.overview = overview;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.releaseDate =releaseDate;
        this.genres = genres;
        this.duration = duration;

    }

    public String getId() {
        return id;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOverview() {
        return overview;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getGenres() {
        return genres;
    }

    public String getDuration() {
        return duration;
    }
}
