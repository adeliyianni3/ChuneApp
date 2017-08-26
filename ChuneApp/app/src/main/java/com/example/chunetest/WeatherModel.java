package com.example.chunetest;

import android.media.*;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by themiya on 11/16/2016.
 */
public class WeatherModel {
    private double lat;
    private double lon;
    private String summary;
    private double cloudCover;
    private double precipProbability;
    private String precipType;
    private double temperature;
    private double humidity;
    private double pressure;
    private String icon;
    private static MediaPlayer[] soundBank;
    private static Map<String, MediaPlayer> ambientSounds;

    public WeatherModel(String summary, double lat, double lon, double cloudCover, double precipPb, String precipType,
                        double temp, double humidity, double pressure,
                        String icon, MediaPlayer[] playerArray, Map<String, MediaPlayer> ambientSounds) {
        this.lat = lat;
        this.lon = lon;
        this.summary = summary;
        this.cloudCover = cloudCover;
        this.precipProbability = precipPb;
        this.precipType = precipType;
        this.temperature = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        this.icon = icon.replace("-", "");
        this.soundBank = playerArray;
        this.ambientSounds = ambientSounds;
    }

    public WeatherModel(MediaPlayer[] playerArray, Map<String, MediaPlayer> ambientSounds) {
        this(null, 0.0, 0.0, 0.0, 0.0, null, 0.0, 0.0, 0.0, "clear-day", playerArray, ambientSounds);
    }

    public String getType() {
        return icon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double value) {
        lat = value;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double value) {
        lon = value;
    }

    public void setWeatherSummary(String summary) {
        this.summary = summary;
    }

    public String getWeatherSummary() {
        return this.summary;
    }

    public void setCloudCover(double cloudCover) {
        this.cloudCover = cloudCover;
    }

    public double getCloudCover() {
        return this.cloudCover;
    }

    public void setPrecipitationPb(double precipPb) {
        this.precipProbability = precipPb;
    }

    public double getPrecipitationPb() {
        return this.precipProbability;
    }

    public void setPrecipitationType(String precipType) {
        this.precipType = precipType;
    }

    public String getPrecipitationType() {
        return this.precipType;
    }

    public void setTemperature(double temp) {
        this.temperature = temp;
    }

    public double getTemperature() {
        return this.temperature;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getHumidity() {
        return this.humidity;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getPressure() {
        return this.pressure;
    }

    public MediaPlayer[] getSoundBank() {
        return soundBank;
    }

    public Map<String, MediaPlayer> getAmbientSounds() {
        return ambientSounds;
    }

    public void setIcon(String icon) {
        this.icon = icon.replace("-", "");
    }
}

