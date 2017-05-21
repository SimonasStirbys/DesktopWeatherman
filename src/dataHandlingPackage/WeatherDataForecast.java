package dataHandlingPackage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class WeatherDataForecast {

	private Map<LocalDateTime, WeatherData> weatherDataCollection;
	private WeatherData currentData;
	private String city;
	private String country;

	public WeatherDataForecast(JsonElement data) {
		this.weatherDataCollection = new HashMap<LocalDateTime, WeatherData>();

		this.city = data.getAsJsonObject().get("city").getAsJsonObject().get("name").getAsString().toUpperCase();
		this.country = convertCountryCodeToName(
				data.getAsJsonObject().get("city").getAsJsonObject().get("country").getAsString());

		JsonArray weatherForecastCollection = (JsonArray) data.getAsJsonObject().get("list");
		for (JsonElement weatherDataInstance : weatherForecastCollection) {
			WeatherData weatherData = new WeatherData((JsonObject) weatherDataInstance);
			weatherDataCollection.put(weatherData.getLastUpdated(), weatherData);
		}
	}

	private String convertCountryCodeToName(String countryCode) {
		Locale location = new Locale("", countryCode);
		return location.getDisplayCountry().toUpperCase();
	}

	public WeatherData getData(String timeOfDay) {
		return weatherDataCollection.get(timeOfDay);
	}

	public WeatherData getDayHighestTemperatureData(int day) {
		WeatherData dayWeatherData = new WeatherData();
		int highestTmperature = -100;

		Set<LocalDateTime> keySet = weatherDataCollection.keySet();
		for (LocalDateTime key : keySet) {
			if(key.getDayOfMonth()==day && weatherDataCollection.get(key).getTemperature()>highestTmperature){
				highestTmperature = weatherDataCollection.get(key).getTemperature();
				dayWeatherData = weatherDataCollection.get(key);
			}
		}
		
		return dayWeatherData;
	}

	public void putData(LocalDateTime date, WeatherData data) {
		weatherDataCollection.put(date, data);
	}

	public void clearAllData() {
		weatherDataCollection.clear();
	}

	public WeatherData getCurrentData() {
		return currentData;
	}

	public void setCurrentData(WeatherData currentData) {
		this.currentData = currentData;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountry() {
		return country;
	}
}
