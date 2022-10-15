package com.company.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.processing.Generated;
import java.util.Collections;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "dt",
        "main",
        "weather",
        "clouds",
        "wind",
        "visibility",
        "pop",
        "sys",
        "dt_txt"
})
@Generated("jsonschema2pojo")
public class Forecast {

    @JsonProperty("dt")
    private Integer dt;
    @JsonProperty("main")
    private Weather main;
    @JsonProperty("weather")
    private List<WeatherDescription> weather = Collections.EMPTY_LIST;
    @JsonProperty("clouds")
    private Cloud clouds;
    @JsonProperty("wind")
    private Wind wind;
    @JsonProperty("visibility")
    private Integer visibility;
    @JsonProperty("pop")
    private Double pop;
    @JsonProperty("sys")
    private Sys sys;
    @JsonProperty("dt_txt")
    private String dt_txt;

    @JsonProperty("dt")
    public Integer getDt() {
        return dt;
    }

    @JsonProperty("dt")
    public void setDt(Integer dt) {
        this.dt = dt;
    }

    @JsonProperty("main")
    public Weather getMain() {
        return main;
    }

    @JsonProperty("main")
    public void setMain(Weather main) {
        this.main = main;
    }

    @JsonProperty("weather")
    public List<WeatherDescription> getWeather() {
        return weather;
    }

    @JsonProperty("weather")
    public void setWeather(List<WeatherDescription> weatherDescription) {
        this.weather = weatherDescription;
    }

    @JsonProperty("clouds")
    public Cloud getClouds() {
        return clouds;
    }

    @JsonProperty("clouds")
    public void setClouds(Cloud cloud) {
        this.clouds = cloud;
    }

    @JsonProperty("wind")
    public Wind getWind() {
        return wind;
    }

    @JsonProperty("wind")
    public void setWind(Wind wind) {
        this.wind = wind;
    }

    @JsonProperty("visibility")
    public Integer getVisibility() {
        return visibility;
    }

    @JsonProperty("visibility")
    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    @JsonProperty("pop")
    public Double getPop() {
        return pop;
    }

    @JsonProperty("pop")
    public void setPop(Double pop) {
        this.pop = pop;
    }

    @JsonProperty("sys")
    public Sys getSys() {
        return sys;
    }

    @JsonProperty("sys")
    public void setSys(Sys sys) {
        this.sys = sys;
    }

    @JsonProperty("dt_txt")
    public String getDt_txt() {
        return dt_txt;
    }

    @JsonProperty("dt_txt")
    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "dt=" + dt +
                ", main=" + main +
                ", weather=" + weather +
                ", clouds=" + clouds +
                ", wind=" + wind +
                ", visibility=" + visibility +
                ", pop=" + pop +
                ", sys=" + sys +
                ", dtTxt='" + dt_txt + '\'' +
                '}';
    }
}