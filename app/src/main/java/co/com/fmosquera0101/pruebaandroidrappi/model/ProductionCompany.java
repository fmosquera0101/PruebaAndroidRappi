package co.com.fmosquera0101.pruebaandroidrappi.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @Autor Fredy Mosquera Lemus
 * @Date 26/08/2018
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "logo_path",
        "name",
        "origin_country"
})

public class ProductionCompany {

    @JsonProperty("id")
    public  int id;

    @JsonProperty("logo_path")
    public String logoPath;

    @JsonProperty("name")
    public String name;

    @JsonProperty("origin_country")
    public String originCountry;

}
