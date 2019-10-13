package com.wawa_applications.wawa_tabor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LineInfo {

    @Expose
    @SerializedName("Lat")
    private Double lat;

    @Expose
    @SerializedName("Lon")
    private Double lon;

    @Expose
    @SerializedName("Time")
    private String time;

    @Expose
    @SerializedName("Lines")
    private String line;

    @Expose
    @SerializedName("Brigade")
    private String brigade;

    public LineInfo(Double lat, Double lon, String time, String line, String brigade) {
        this.lat = lat;
        this.lon = lon;
        this.time = time;
        this.line = line;
        this.brigade = brigade;
    }
}