package co.com.fmosquera0101.pruebaandroidrappi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "page",
        "results",
        "total_results",
        "total_pages",
        "dates"
})
public class Movies {

    @JsonProperty("page")
    public int page;
    @JsonProperty("results")
    public List<Movie> movies = null;
    @JsonProperty("total_results")
    public int totalResults;
    @JsonProperty("total_pages")
    public int totalPages;
    @JsonProperty("dates")
    public Dates dates;


}