class Weather {
   
  WeatherType type;
  float temperature;
  float humidity;
  float cloudCover;
  float windSpeed;
  
  public Weather(JSONObject json) {
    this.temperature = json.getFloat("temperature");
    this.humidity = json.getFloat("humidity");
    this.cloudCover = json.getFloat("cloudCover");
    this.windSpeed = json.getFloat("windSpeed");
    String temp = json.getString("icon");
    temp = temp.replace("-", "");
    String typeString = temp;
    
    try {
      this.type = WeatherType.valueOf(typeString);
    }
    catch (IllegalArgumentException e) {
      throw new RuntimeException(typeString + " is not a valid value for enum WeatherType.");
    }                        
  }
  public WeatherType getType() {
    return type;
  }
  
  public String toString() {
      String output = type.toString() + " " + type.getWav() + ": ";
      output += "(" + temperature + ")";
      output += " " + humidity + ")";
      output += " " + cloudCover + ")";
      output += " " + windSpeed;
      return output;
  }
  public float getTemp(){
    return temperature;
  }
  public float getHumidity(){
    return humidity;
  }
  public float getCloudCover(){
    return cloudCover;
  }
  public float getWindSpeed(){
    return windSpeed;
  }
  public void setTemp(float t){
    temperature = t;
  }
  public void setHumidity(float h){
    humidity = h;
  }
  public void setCloudCover(float c){
    cloudCover = c;
  }
  public void setWindSpeed(float w){
    windSpeed = w;
  }
  public void setType(WeatherType t){
    type = t;
  }
}