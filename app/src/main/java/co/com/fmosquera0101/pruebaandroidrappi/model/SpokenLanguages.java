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
        "iso_639_1",
        "name"
})
public class SpokenLanguages {

    @JsonProperty("iso_639_1")
    public String iso6391;

    @JsonProperty("name")
    public String name;
}
