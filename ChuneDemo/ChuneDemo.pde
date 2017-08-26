import ddf.minim.*;
import controlP5.*;
import java.util.*;
import beads.*;
import org.jaudiolibs.beads.*;

Minim minim;
Button event1;
Button event2;
Button event3;

float wait;

Button clearday;
Button clearnight;
Button rain;
Button snow;
Button sleet;
Button windy;
Button fog;
Button cloudy;
Button partcday;
Button partcnight;

AudioPlayer player;
Weather one;

  AudioContext ac; //thing/core that runs audio in Beads lib
  ControlP5 cp5;
  Gain alertGain;
  WavePlayer alt;
  
  Slider temp;
  Slider hum;
  Slider wind;
  Slider cloud;
  
String json1 = "ExampleData_1.json";
String json2 = "ExampleData_2.json";
String json3 = "ExampleData_3.json";

WeatherListener list;

void setup(){
  size(512, 600);
  cp5 = new ControlP5(this);
  
clearday = new Button(cp5, "Clear Day");
  clearday.setPosition(50,400);
  clearday.setWidth(75);
  clearday.setHeight(50);
clearnight = new Button(cp5, "Clear Night");
  clearnight.setPosition(135,400);
  clearnight.setWidth(75);
  clearnight.setHeight(50);
rain = new Button(cp5, "Rain");
  rain.setPosition(220,400);
  rain.setWidth(75);
  rain.setHeight(50);
snow = new Button(cp5, "Snow");
  snow.setPosition(50,475);
  snow.setWidth(75);
  snow.setHeight(50);
sleet = new Button(cp5, "Sleet");
  sleet.setPosition(135,475);
  sleet.setWidth(75);
  sleet.setHeight(50);
windy = new Button(cp5, "Wind");
  windy.setPosition(220,475);
  windy.setWidth(75);
  windy.setHeight(50);
fog = new Button(cp5, "Fog");
  fog.setPosition(305,400);
  fog.setWidth(75);
  fog.setHeight(50);
cloudy = new Button(cp5, "Cloudy");
  cloudy.setPosition(305,475);
  cloudy.setWidth(75);
  cloudy.setHeight(50);
partcday = new Button(cp5, "Partly Cloudy \nDay");
  partcday.setPosition(390,475);
  partcday.setWidth(75);
  partcday.setHeight(50);
partcnight = new Button(cp5, "Partly Cloudy \nNight");
  partcnight.setPosition(390,400);
  partcnight.setWidth(75);
  partcnight.setHeight(50);
  
  
  temp = new Slider(cp5, "Temperature");
  hum = new Slider(cp5, "Humidity");
  wind = new Slider(cp5, "Wind Speed");
  cloud = new Slider(cp5, "Cloud Coverage");
   
  temp.setPosition(width/2-100,200);
  hum.setPosition(width/2-100,250);
  cloud.setPosition(width/2-100,300);
  wind.setPosition(width/2-100,350);
  
  event1 = new Button(cp5, "Weather Scenario 1");
  event1.setPosition(((width-120)/2)-100,100);
  event1.setWidth(100);
  event1.setHeight(50);

  event2 = new Button(cp5, "Weather Scenario 2");
  event2.setPosition(((width-100)/2),100);
  event2.setWidth(100);
  event2.setHeight(50);
  
  event3 = new Button(cp5, "Weather Scenario 3");
  event3.setPosition(((width-80)/2)+100,100);
  event3.setWidth(100);
  event3.setHeight(50);
  
  ac = new AudioContext();
  alertGain = new Gain(ac, 1);
  
  alt = new WavePlayer(ac, 440, Buffer.SINE);
  alt.pause(true);
  alertGain.addInput(alt);
  ac.out.addInput(alertGain);
  ac.start();
  list = new WeatherListener();
  minim = new Minim(this);
}
void draw() {
  background(0);
  if (one !=null) {
      list.sonifyWeather(one);
      one.setTemp(temp.getValue());
      one.setHumidity(hum.getValue()/100);
      one.setCloudCover(cloud.getValue()/100);
      one.setWindSpeed(wind.getValue());
  }
  if ( millis() - wait > 900 ) {
    wait = millis();
    alertGain.setGain(0);
  }
}

void mousePressed() { //when moused is pressed
  if (event1.isPressed()) {
    if (player != null) {
      player.pause();
    }
    one = new Weather(loadJSONArray(json1).getJSONObject(0).getJSONObject("currently"));
    player = minim.loadFile(one.getType().getWav());
    temp.setValue(one.getTemp());
    hum.setValue(one.getHumidity()*100);
    cloud.setValue(one.getCloudCover()*100);
    wind.setValue(one.getWindSpeed());
    player.loop();
    wait = millis();
  } else if (event2.isPressed()) {
    if (player != null) {
      player.pause();
    }
    one = new Weather(loadJSONArray(json2).getJSONObject(0).getJSONObject("currently"));
    player = minim.loadFile(one.getType().getWav());
    temp.setValue(one.getTemp());
    hum.setValue(one.getHumidity()*100);
    cloud.setValue(one.getCloudCover()*100);
    wind.setValue(one.getWindSpeed());
    player.loop();
    
  } else if (event3.isPressed()) {
    if (player != null) {
      player.pause();
    }
    one = new Weather(loadJSONArray(json3).getJSONObject(0).getJSONObject("currently"));
    player = minim.loadFile(one.getType().getWav());
    temp.setValue(one.getTemp());
    hum.setValue(one.getHumidity()*100);
    cloud.setValue(one.getCloudCover()*100);
    wind.setValue(one.getWindSpeed());
    player.loop();
  } else if (clearday.isPressed()) {
    if (one != null) {
      player.pause();
      one.setType(WeatherType.clearday);
      player = minim.loadFile(one.getType().getWav());
      player.loop();
    }
  } else if (clearnight.isPressed()) {
    if (one != null) {
      player.pause();
      one.setType(WeatherType.clearnight);
      player = minim.loadFile(one.getType().getWav());
      player.loop();
    }
  } else if (rain.isPressed()) {
    if (one != null) {
      player.pause();
      one.setType(WeatherType.rain);
      player = minim.loadFile(one.getType().getWav());
      player.loop();
    }
  } else if (snow.isPressed()) {
    if (one != null) {
      player.pause();
      one.setType(WeatherType.snow);
      player = minim.loadFile(one.getType().getWav());
      player.loop();
    }
  } else if (sleet.isPressed()) {
    if (one != null) {
      player.pause();
      one.setType(WeatherType.sleet);
      player = minim.loadFile(one.getType().getWav());
      player.loop();
    }
  } else if (windy.isPressed()) {
    if (one != null) {
      player.pause();
      one.setType(WeatherType.wind);
      player = minim.loadFile(one.getType().getWav());
      player.loop();
    }
  } else if (fog.isPressed()) {
    if (one != null) {
      player.pause();
      one.setType(WeatherType.fog);
      player = minim.loadFile(one.getType().getWav());
      player.loop();
    }
  } else if (cloudy.isPressed()) {
    if (one != null) {
      player.pause();
      one.setType(WeatherType.cloudy);
      player = minim.loadFile(one.getType().getWav());
      player.loop();
    }
  } else if (partcday.isPressed()) {
    if (one != null) {
      player.pause();
      one.setType(WeatherType.partlycloudyday);
      player = minim.loadFile(one.getType().getWav());
      player.loop();
    }
  } else if (partcnight.isPressed()) {
    if (one != null) {
      player.pause();
      one.setType(WeatherType.partlycloudynight);
      player = minim.loadFile(one.getType().getWav());
      player.loop();
    }
  }
}