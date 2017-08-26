class WeatherListener {
  public void sonifyWeather(Weather weather) {
    float temp = weather.getTemp();
    float hum = weather.getHumidity();
    float clCov = weather.getCloudCover();
    float wSpeed = weather.getWindSpeed();
    
    if ( millis() - wait > (clCov+200)+(clCov*300)) {
      alt.pause(true);
    } else {
      if ((millis() - wait) > (wSpeed*2)) {
        alt.setFrequency(300+(hum*100));
      } else {
        alt.setFrequency(200);
      }
      alertGain.setGain(temp/100 + .1);
      alt.pause(false);
    }
  }
}