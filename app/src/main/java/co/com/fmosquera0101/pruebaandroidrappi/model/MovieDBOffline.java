package co.com.fmosquera0101.pruebaandroidrappi.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class MovieDBOffline {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int idPrimaeryKey;

    private String id;
    private String title;
    private String originalLanguage;
    private String overview;
    private String popularity;
    private String posterPath;
    private String releaseDate;
    private String genres;
    private String duration;
    private String isTopRated;
    private String isPopular;
    private String isUpcomming;

    public MovieDBOffline(int idPrimaeryKey, String id, String title, String originalLanguage, String overview, String popularity,
                          String posterPath, String releaseDate, String genres, String duration,
                          String isTopRated, String isPopular, String isUpcomming){
        this.idPrimaeryKey = idPrimaeryKey;
        this.id = id;
        this.title = title;
        this.originalLanguage = originalLanguage;
        this.overview = overview;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.releaseDate =releaseDate;
        this.genres = genres;
        this.duration = duration;
        this.isTopRated = isTopRated;
        this.isPopular = isPopular;
        this.isUpcomming = isUpcomming;

    }

    @NonNull
    public int getIdPrimaeryKey() {
        return idPrimaeryKey;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
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

    public String getIsTopRated() {
        return isTopRated;
    }

    public String getIsPopular() {
        return isPopular;
    }

    public String getIsUpcomming() {
        return isUpcomming;
    }
}
