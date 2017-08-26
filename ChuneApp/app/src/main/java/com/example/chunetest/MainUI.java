package com.example.chunetest;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.example.chunetest.HTTPHandler.loadHourlyModel;
import static com.example.chunetest.HTTPHandler.loadModel;

public class MainUI extends AppCompatActivity {

    static int index;
    static double myLat;
    static double myLon;
    Random rand = new Random();
    double minLat = -90;
    double maxLat = 90;
    double minLon = -180;
    double maxLon = 180;

    final int RAIN_CODE = 0;
    final int SNOW_CODE = 1;
    final int SLEET_CODE = 2;
    final int THUNDER_CODE = 3;
    final int TORNADO_CODE = 4;

    private static final Map<String, Location> Locations = new HashMap<String, Location>(){
        {
            put("Cancun", new Location(21.1619,86.8515));
            put("London", new Location(51.5074,0.1278));
            put("Moscow", new Location(55.7558,37.6173));
            put("Random Location", new Location(41.9028,12.4964));
        }
    };

    MediaPlayer[] players;
    Map<String, MediaPlayer> ambientSounds;
    GPSTracker gps;
    WeatherModel current;
    WeatherModel hourly;
    Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);

        players = this.createSoundBank();
        ambientSounds = this.createAmbientSounds();
        current = new WeatherModel(players, ambientSounds);
        hourly = new WeatherModel(players, ambientSounds);
        gps = new GPSTracker(MainUI.this);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Object [] LocationArr = new Object[Locations.keySet().size() + 2];
        LocationArr[0]="[Please Select A Location]";
        LocationArr[1]="Current Location";
        Object[]keys = Locations.keySet().toArray();
        Arrays.sort(keys);
        int counter = 2;
        for (Object given: keys) {
            LocationArr[counter++] = given;
        }
        ArrayAdapter<CharSequence> adapter =  new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, LocationArr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 1) { //Current Location
                    System.out.println(position);
                    if (gps.canGetLocation()) {
                        myLat = gps.getLatitude();
                        myLon = gps.getLongitude();
                    }
                    loadData(myLat, myLon);

                } else if (position == 2) { //Cancun
                    System.out.println(position);
                    loadData(Locations.get("Cancun").getLatitude(), Locations.get("Cancun").getLongitude());

                } else if (position == 3) { //London
                    System.out.println(position);
                    loadData(Locations.get("London").getLatitude(), Locations.get("London").getLongitude());

                } else if (position == 4) { //Moscow
                    System.out.println(position);
                    loadData(Locations.get("Moscow").getLatitude(), Locations.get("Moscow").getLongitude());

                } else if (position == 5) { //Random
                    System.out.println(position);
                    double randomLat = (rand.nextDouble() * (maxLat - minLat)) + minLat;
                    double randomLon = (rand.nextDouble() * (maxLon - minLon)) + minLon;
                    loadData(randomLat, randomLon);
                } else { //Default Selection
                    System.out.println(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                System.out.println("Nothing");
            }

        });
        loadUI(0.0,"clearday",0.0,0.0,0.0);
        Toast.makeText(getApplicationContext(),
                "Loading Sound and Location Data Complete", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        players = this.createSoundBank();
        ambientSounds = this.createAmbientSounds();
        current = new WeatherModel(players, ambientSounds);
        hourly = new WeatherModel(players, ambientSounds);
        gps = new GPSTracker(MainUI.this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        for (int i = 0; i < players.length; i++) {
            players[i].release();
        }
        for (MediaPlayer player : ambientSounds.values()) {
            player.release();
        }
    }

    private int getImage(String weatherSummary) {
        if (weatherSummary.contains("clearday")) {
            return R.drawable.clear_day;
        } else if (weatherSummary.contains("clearnight")) {
            return R.drawable.clear_night;
        } else if (weatherSummary.contains("cloudy")) {
            return R.drawable.cloudy;
        } else if (weatherSummary.contains("fog")) {
            return R.drawable.fog;
        } else if (weatherSummary.contains("hail")) {
            return R.drawable.hail;
        } else if (weatherSummary.contains("partlycloudyday")) {
            return R.drawable.partly_cloudy_day;
        } else if (weatherSummary.contains("partlycloudynight")) {
            return R.drawable.partly_cloudy_night;
        } else if (weatherSummary.contains("rain")) {
            return R.drawable.rain;
        } else if (weatherSummary.contains("sleet")) {
            return R.drawable.sleet;
        } else if (weatherSummary.contains("snow")) {
            return R.drawable.snow;
        } else if (weatherSummary.contains("thunderstorm")) {
            return R.drawable.thunderstorm;
        } else if (weatherSummary.contains("tornado")) {
            return R.drawable.tornado;
        } else if (weatherSummary.contains("wind")) {
            return R.drawable.wind;
        } else {
            return R.drawable.blank;
        }
    }

    public MediaPlayer[] createSoundBank() {
        String[] noteFiles = new String[0];
        try {
            noteFiles = getAssets().list("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //MediaPlayer[] list = new MediaPlayer[36];
        List<MediaPlayer> playerList = new ArrayList<>();

        for (int i = 0; i < noteFiles.length; i++) {
                Log.d("File Names", noteFiles[i]);
                if (noteFiles[i].contains("wav")) {
                    String file = noteFiles[i].replace(".wav", "");
                    int id = this.getResources().getIdentifier(file, "raw", this.getPackageName());
                    final MediaPlayer player = MediaPlayer.create(getApplicationContext(), id);
                    Log.d("Media Players", "Media Player " + i + " created successfully");
                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            player.pause();
                            player.seekTo(0);
                        }
                    });
                    //list[i] = player;
                    playerList.add(player);
                }
        }

        //MediaPlayer[] list = (MediaPlayer[]) playerList.toArray();
        MediaPlayer[] list = new MediaPlayer[36];
        for (int i = 0; i < 36; i++) {
            list[i] = playerList.get(i);
        }
        return list;
    }

    public Map<String, MediaPlayer> createAmbientSounds() {
        Map<String, MediaPlayer> sounds = new HashMap<>();
        sounds.put("clearday", new MediaPlayer().create(getApplicationContext(), R.raw.clearday));
        sounds.put("clearnight", new MediaPlayer().create(getApplicationContext(), R.raw.clearnight));
        sounds.put("cloudy", new MediaPlayer().create(getApplicationContext(), R.raw.cloud));
        sounds.put("fog", new MediaPlayer().create(getApplicationContext(), R.raw.fog));
        sounds.put("partlycloudyday", new MediaPlayer().create(getApplicationContext(), R.raw.partlycloudyday));
        sounds.put("partlycloudynight", new MediaPlayer().create(getApplicationContext(), R.raw.partlycloudynight));
        sounds.put("rain", new MediaPlayer().create(getApplicationContext(), R.raw.rain));
        sounds.put("sleet", new MediaPlayer().create(getApplicationContext(), R.raw.sleet));
        sounds.put("snow", new MediaPlayer().create(getApplicationContext(), R.raw.snow));
        sounds.put("wind", new MediaPlayer().create(getApplicationContext(), R.raw.wind));
        sounds.put("tornado", new MediaPlayer().create(getApplicationContext(), R.raw.siren));

        for (final MediaPlayer player : sounds.values()) {
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    player.pause();
                    player.seekTo(0);
                }
            });
        }

        return sounds;
    }

    private void loadUI(double temperature, String weatherSummary, double cloudCoverage, double humidity, double precipProb) {
        weatherSummary.replaceAll("\"", "");
        TextView temp = (TextView) findViewById(R.id.temperature);
        TextView cloudCover = (TextView) findViewById(R.id.cloudCoverage);
        TextView hum = (TextView) findViewById(R.id.humidity);
        TextView precip = (TextView) findViewById(R.id.precipProb);
        if (weatherSummary == null) {
            weatherSummary = "err";
        }
        int icon = getImage(weatherSummary);
        ImageView image = (ImageView) findViewById(R.id.summary);
        image.setImageResource(icon);
        if (!(temperature == 0.0 && cloudCoverage == 0.0 && humidity == 0.0 && precipProb == 0.0)) {
            temp.setText(temperature+" F");
            cloudCover.setText(cloudCoverage+"");
            hum.setText(humidity+"");
            precip.setText(precipProb+"");
        } else {
            temp.setText("---");
            cloudCover.setText("---");
            hum.setText("---");
            precip.setText("---");
        }
    }

    private void loadSound() {
        current.getAmbientSounds().get(current.getType()).start();
        index = (int) (((current.getTemperature() % 100) / 10) * 3) + (int) Math.floor((current.getHumidity()*100)/33.3);
        if (index < 0) {
            index = 0;
        }
        if (index > 100) {
            index = 100;
        }
        if (current.getPrecipitationPb() <= .50) {
            current.getSoundBank()[index].setVolume(.50f, .50f);
        } else {
            current.getSoundBank()[index].setVolume((float) current.getPrecipitationPb(), (float) current.getPrecipitationPb());
        }
        current.getSoundBank()[index].start();
        if (hourly.getType().equalsIgnoreCase("rain")) {
            Calendar now = Calendar.getInstance();
            ReminderManager.setReminder(getApplicationContext(), RAIN_CODE, "rain", now);
        } else if (hourly.getType().equalsIgnoreCase("snow")) {
            Calendar now = Calendar.getInstance();
            ReminderManager.setReminder(getApplicationContext(), SNOW_CODE, "snow", now);
        } else if (hourly.getType().equalsIgnoreCase("sleet")) {
            Calendar now = Calendar.getInstance();
            ReminderManager.setReminder(getApplicationContext(), SLEET_CODE, "sleet", now);
        } else if (hourly.getType().equalsIgnoreCase("thunderstorm")) {
            Calendar now = Calendar.getInstance();
            ReminderManager.setReminder(getApplicationContext(), THUNDER_CODE, "thunder storm", now);
        } else if (hourly.getType().equalsIgnoreCase("tornado")) {
            Calendar now = Calendar.getInstance();
            ReminderManager.setReminder(getApplicationContext(), TORNADO_CODE, "tornado", now);
            hourly.getAmbientSounds().get("tornado").start();
        }
    }

    class httpconnection extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            current.setLat(myLat);
            current.setLon(myLon);
            hourly.setLat(myLat);
            hourly.setLon(myLon);
            loadModel(current);
            loadHourlyModel(hourly);
            Log.d("Weather Status", "Weather loaded for Lat. " + myLat + ", Lon. " + myLon + ". Current Temp. is " + current.getTemperature() + " F " + current.getType());
            Log.d("Hourly Weather Status", "Weather loaded for Lat. " + myLat + ", Lon. " + myLon + ". Current Temp. is " + hourly.getTemperature() + " F " + hourly.getType());
            return null;
        }
        protected void onPostExecute(Void result) {
            loadUI(current.getTemperature(), current.getType()
                    , current.getCloudCover(), current.getHumidity(), current.getPrecipitationPb());
            loadSound();
            Toast.makeText(getApplicationContext(),
                    "Loading Weather Complete", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadData(double lat, double lon) {
        myLat = lat;
        myLon = lon;
        new httpconnection().execute();
    }

    public void replaySound(View view) {
        current.getAmbientSounds().get(current.getType()).start();
        current.getSoundBank()[index].start();
    }
}
