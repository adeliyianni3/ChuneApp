package com.example.chunetest;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.example.chunetest.HTTPHandler.loadModel;

public class MainActivity extends AppCompatActivity {

    double myLat;
    double myLon;
    MediaPlayer[] players;
    Map<String, MediaPlayer> ambientSounds;
    WeatherModel current;
    MediaPlayer ambient;
    LocationManager lm;
    Location currentLocation;
    Geocoder myLocation;
    List<Address> locationList;
    Button currentButton;
    Button londonButton;
    Button moscowButton;
    Button randomButton;
    Button cancunButton;
    TextView temperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentButton = (Button) findViewById(R.id.currentButton);
        londonButton = (Button) findViewById(R.id.londonButton);
        moscowButton = (Button) findViewById(R.id.moscowButton);
        randomButton = (Button) findViewById(R.id.randomButton);
        cancunButton = (Button) findViewById(R.id.cancunButton);
        temperature = (TextView) findViewById(R.id.tempTextView);

        players = this.createSoundBank();
        ambientSounds = this.createAmbientSounds();
        current = new WeatherModel(players, ambientSounds);

        currentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*getLocation();
                if (currentLocation != null) {
                    try {
                        locationList = myLocation.getFromLocation(myLat, myLon, 1);
                        if (locationList.size() >= 1) {
                            currentButton.setText(locationList.get(0).toString());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } */
                myLat = 33.7756;
                myLon = 84.3963;
                loadData(temperature);
                int weatherId = getResources().getIdentifier(current.getType(), "raw", getPackageName());
                int index = (int) ((current.getTemperature() % 100) / 10) * 2;
                ambient = MediaPlayer.create(getApplicationContext(), weatherId);
                current.getSoundBank()[index].start();
                ambient.start();
            }
        });

        londonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myLat = 51.5074;
                myLon = 0.1278;
                loadData(temperature);
                int weatherId = getResources().getIdentifier(current.getType(), "raw", getPackageName());
                ambient = MediaPlayer.create(getApplicationContext(), weatherId);
                int index = (int) ((current.getTemperature() % 100) / 10);
                current.getSoundBank()[index].start();
                ambient.start();
            }
        });

        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myLat = 41.9028;
                myLon = 12.4964;
                loadData(temperature);
                int weatherId = getResources().getIdentifier(current.getType(), "raw", getPackageName());
                ambient = MediaPlayer.create(getApplicationContext(), weatherId);
                int index = (int) ((current.getTemperature() % 100) / 10);
                current.getSoundBank()[index].start();
                ambient.start();
            }
        });

        moscowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myLat = 55.7558;
                myLon = 37.6173;
                loadData(temperature);
                int weatherId = getResources().getIdentifier(current.getType(), "raw", getPackageName());
                ambient = MediaPlayer.create(getApplicationContext(), weatherId);
                int index = (int) ((current.getTemperature() % 100) / 10);
                current.getSoundBank()[index].start();
                ambient.start();
            }
        });

        cancunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myLat = 21.1619;
                myLon = 86.8515;
                loadData(temperature);
                int weatherId = getResources().getIdentifier(current.getType(), "raw", getPackageName());
                ambient = MediaPlayer.create(getApplicationContext(), weatherId);
                int index = (int) ((current.getTemperature() % 100) / 10);
                current.getSoundBank()[index].start();
                ambient.start();
            }
        });
    }

    public MediaPlayer[] createSoundBank() {
        String[] noteFiles = new String[0];
        try {
            noteFiles = getAssets().list("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* for (int i = 0; i < noteFiles.length; i++) {
            Log.d("Asset List", i + " " + noteFiles[i]);
        } */

        //MediaPlayer[] list = new MediaPlayer[36];
        List<MediaPlayer> playerList = new ArrayList<>();

        for (int i = 0; i < noteFiles.length; i++) {
            if (i != 27 && i != 28 && i != 29) {
                Log.d("File Names", noteFiles[i]);
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
        sounds.put("cloud", new MediaPlayer().create(getApplicationContext(), R.raw.cloud));
        sounds.put("partlycloudyday", new MediaPlayer().create(getApplicationContext(), R.raw.partlycloudyday));
        sounds.put("partlycloudynight", new MediaPlayer().create(getApplicationContext(), R.raw.partlycloudynight));
        sounds.put("rain", new MediaPlayer().create(getApplicationContext(), R.raw.rain));
        sounds.put("sleet", new MediaPlayer().create(getApplicationContext(), R.raw.sleet));
        sounds.put("snow", new MediaPlayer().create(getApplicationContext(), R.raw.snow));
        sounds.put("wind", new MediaPlayer().create(getApplicationContext(), R.raw.wind));

        for (final MediaPlayer player : sounds.values()) {
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    player.reset();
                }
            });
        }

        return sounds;
    }

    public void getLocation() {
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        myLocation = new Geocoder(getApplicationContext(), Locale.getDefault());

        final LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                if ((ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                lm.removeUpdates(this);
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
                myLat = currentLocation.getLatitude();
                myLon = currentLocation.getLongitude();
            }

            public void onProviderDisabled(String arg0) {
                // TODO Auto-generated method stub

            }

            public void onProviderEnabled(String arg0) {
                // TODO Auto-generated method stub

            }

            public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
                // TODO Auto-generated method stub

            }
        };

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);

        if(currentLocation != null) {
            myLat = currentLocation.getLatitude();
            myLon = currentLocation.getLongitude();
        } else {
            lm.removeUpdates(locationListener);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
        }
    }

    class httpconnection extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            current.setLat(myLat);
            current.setLon(myLon);
            loadModel(current);
            Log.d("Weather Status", "Weather loaded for Lat. " + myLat + ", Lon. " + myLon + ". Current Temp. is " + current.getTemperature() + " F " + current.getType());
            return null;
        }
        protected void onPostExecute(Void result) {
            temperature.setText(current.getTemperature()+ " F");
        }
    }

    public void loadData(View view) {
        new httpconnection().execute();
        if (current.getTemperature() == 0.0 && current.getWeatherSummary() == null) {
            temperature.setText("Loading Weather Data, Please Wait...");
        }
        else {
            temperature.setText(current.getTemperature()+ " F");
        }
    }
}
