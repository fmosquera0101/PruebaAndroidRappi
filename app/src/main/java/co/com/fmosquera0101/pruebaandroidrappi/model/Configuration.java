package co.com.fmosquera0101.pruebaandroidrappi.model;


import android.provider.MediaStore;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

/**
 * @Autor Fredy Mosquera Lemus
 * @Date 26/08/2018
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "images",
        "change_keys"
})
public class Configuration {

    @JsonProperty("images")
    public Images images;
    @JsonProperty("change_keys")
    public List<String> changeKeys = null;

}
