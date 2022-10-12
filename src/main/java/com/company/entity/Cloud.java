package com.company.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.processing.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "all"
})
@Generated("jsonschema2pojo")
public class Cloud {

    @JsonProperty("all")
    private Integer cloudiness;

    @JsonProperty("all")
    public Integer getCloudiness() {
        return cloudiness;
    }

    @JsonProperty("all")
    public void setCloudiness(Integer cloudiness) {
        this.cloudiness = cloudiness;
    }

}