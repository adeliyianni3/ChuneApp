package com.example.chunetest;

import android.util.Log;

import java.net.*;
import java.io.*;
import java.util.regex.Pattern;
/**
 * Created by themiya on 11/16/2016.
 */
public class HTTPHandler {
    private static final String apiKey = "a6a5b0cc356600fe016c3a5413e27e73";
    private static final String apiUrl = "https://api.darksky.net/forecast/";

    public static String getWeather(double latitude, double longitude) {
        String query = apiUrl + apiKey + "/" + latitude + "," + longitude;
        String result = null;

        try {
            URL myApi = new URL(query);
            HttpURLConnection conn = (HttpURLConnection) myApi.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            result = in.readLine();

            conn.disconnect();
            in.close();
            return result;
        } catch (Exception e) {
            System.out.println("Failed to reach weather api" + " " + e);
        }
        Log.d("Weather Raw", result);
        return result;
    }

    public static void loadModel(WeatherModel model) {
        //String weatherData = getWeather(42.3601, -71.0589);
        String weatherData = getWeather(model.getLat(), model.getLon());
        weatherData = weatherData.split("\"currently\"")[1];
        weatherData = weatherData.split(Pattern.quote("}"))[0];
        weatherData = weatherData.substring(2, weatherData.length());
        System.out.println(weatherData);
        String[]values = weatherData.split(",");

        for (String x: values) {
            if (x.contains("cloudCover")) {
                x = x.split(":")[1];
                model.setCloudCover(Double.parseDouble(x));
            }
            if (x.contains("humidity")) {
                x = x.split(":")[1];
                model.setHumidity(Double.parseDouble(x));
            }
            if (x.contains("precipProbability")) {
                x = x.split(":")[1];
                model.setPrecipitationPb(Double.parseDouble(x));
            }
            if (x.contains("precipType")) {
                x = x.split(":")[1];
                model.setPrecipitationType(x.replaceAll("\"", ""));
            }
            if (x.contains("pressure")) {
                x = x.split(":")[1];
                model.setPressure(Double.parseDouble(x));
            }
            if (x.contains("temperature")) {
                x = x.split(":")[1];
                model.setTemperature(Double.parseDouble(x));
            }
            if (x.contains("summary")) {
                x = x.split(":")[1];
                model.setWeatherSummary(x.replaceAll("\"", ""));
            }
            if (x.contains("icon")) {
                x = x.split(":") [1];
                model.setIcon(x.replaceAll("\"", ""));
            }
        }
    }

    public static void loadHourlyModel(WeatherModel model) {
        String weatherData = getWeather(model.getLat(), model.getLon());
        weatherData = weatherData.split("\"hourly\"")[1];
        weatherData = weatherData.split(Pattern.quote("}"))[0];
        weatherData = weatherData.substring(2, weatherData.length());
        System.out.println(weatherData);
        String[]values = weatherData.split(",");

        for (String x: values) {
            if (x.contains("cloudCover")) {
                x = x.split(":")[1];
                model.setCloudCover(Double.parseDouble(x));
            }
            if (x.contains("humidity")) {
                x = x.split(":")[1];
                model.setHumidity(Double.parseDouble(x));
            }
            if (x.contains("precipProbability")) {
                x = x.split(":")[1];
                model.setPrecipitationPb(Double.parseDouble(x));
            }
            if (x.contains("precipType")) {
                x = x.split(":")[1];
                model.setPrecipitationType(x.replaceAll("\"", ""));
            }
            if (x.contains("pressure")) {
                x = x.split(":")[1];
                model.setPressure(Double.parseDouble(x));
            }
            if (x.contains("temperature")) {
                x = x.split(":")[1];
                model.setTemperature(Double.parseDouble(x));
            }
            if (x.contains("summary")) {
                x = x.split(":")[1];
                model.setWeatherSummary(x.replaceAll("\"", ""));
            }
            if (x.contains("icon")) {
                x = x.split(":")[1];
                model.setIcon(x.replaceAll("\"", ""));
            }
        }
    }
}
