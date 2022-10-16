package com.company.entity;

import com.company.icon.WeatherTypeIcon;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.processing.Generated;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "main", "description", "icon"})
@Generated("jsonschema2pojo")
public class WeatherDescription {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("main")
    private String main;
    @JsonProperty("description")
    private String description;
    @JsonProperty("icon")
    private String icon;

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("main")
    public String getMain() {
        return main;
    }

    @JsonProperty("main")
    public void setMain(String main) {
        this.main = main;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("icon")
    public WeatherTypeIcon getIcon() {
        System.out.println(icon);
        Map<String, WeatherTypeIcon> iconsMapping = Map.of(
                "01", WeatherTypeIcon.CLEAR_SKY,
                "02", WeatherTypeIcon.FEW_CLOUDS,
                "03", WeatherTypeIcon.SCATTERED_CLOUDS,
                "04", WeatherTypeIcon.SHOWER_RAIN,
                "09", WeatherTypeIcon.BROKEN_CLOUDS,
                "10", WeatherTypeIcon.RAIN,
                "11", WeatherTypeIcon.THUNDERSTORM,
                "13", WeatherTypeIcon.SNOW,
                "50", WeatherTypeIcon.MIST);

        return iconsMapping.get(icon.substring(0, 2));
    }

    @JsonProperty("icon")
    public void setIcon(String icon) {
        this.icon = icon;
    }
}