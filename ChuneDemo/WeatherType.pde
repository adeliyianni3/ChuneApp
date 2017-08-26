import java.util.*;

enum WeatherType { 
  clearday("ClearDay.wav"), clearnight("ClearNight.wav"), rain("Rain.wav"),
  snow("Snow.wav"), sleet("Sleet.wav"), wind("Wind.wav"), fog("Fog.wav"), cloudy("Cloud.wav"),
  partlycloudyday("PartlyCloudyDay.wav"), partlycloudynight("PartlyCloudyNight.wav");
  
  private String soundName;

  private WeatherType(final String soundFile) {
    soundName = soundFile;
  }

  public String getWav() {
    return soundName;
  }
  public WeatherType valueOf() {
    return this;
  }
}