package dataHandlingPackage;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;

import com.google.gson.JsonObject;
import mainPackage.DataIdentifierEnum;

public class WeatherData {
	private String weatherDescription, city, country, weatherIcon;
	private int temperature, cloudiness, windSpeed, windDirection, humidity;
	private LocalDateTime sunset, sunrise, lastUpdated;

	public WeatherData() {
		
	}

	
	public WeatherData(JsonObject jsonObject) {
		DataParser dataParser = new DataParser();

//		this.setCity(dataParser.extractData(jsonObject, DataIdentifierEnum.CITY.getDataIdentifier()));
//		String country = dataParser.extractData(jsonObject, DataIdentifierEnum.COUNTRY.getDataIdentifier());
//		this.setCountry(convertCountryCodeToName(country));
		this.setWeatherType(dataParser.extractData(jsonObject, DataIdentifierEnum.WEATHER.getDataIdentifier()));
		
		double temperature = Double.parseDouble(dataParser.extractData(jsonObject, DataIdentifierEnum.TEMPERATURE.getDataIdentifier()));
		this.setTemperature((int) Math.round(temperature));
		double humidity = Double.parseDouble(dataParser.extractData(jsonObject, DataIdentifierEnum.HUMIDITY.getDataIdentifier()));
		this.setHumidity((int) Math.round(humidity));
		double cloudiness = Double.parseDouble(dataParser.extractData(jsonObject, DataIdentifierEnum.CLOUDINESS.getDataIdentifier()));
		this.setCloudiness((int) Math.round(cloudiness));
		double windSpeed = Double.parseDouble(dataParser.extractData(jsonObject, DataIdentifierEnum.WIND_SPEED.getDataIdentifier()));
		this.setWindSpeed((int) Math.round(windSpeed));

		String icon = dataParser.extractData(jsonObject, DataIdentifierEnum.ICON.getDataIdentifier());
		this.setweatherIcon(extractIconData(icon.toLowerCase()));

//		String sunrise = dataParser.extractData(jsonObject, DataIdentifierEnum.SUNRISE.getDataIdentifier());
//		this.setSunrise(convertUnixToHumanTime(sunrise));
//		String sunset = dataParser.extractData(jsonObject, DataIdentifierEnum.SUNSET.getDataIdentifier());
//		this.setSunset(convertUnixToHumanTime(sunset));
		String lastUpdated = dataParser.extractData(jsonObject, DataIdentifierEnum.LAST_UPPDATED.getDataIdentifier());
		this.setLastUpdated(convertUnixToHumanTime(lastUpdated));
	}
	
	private String convertCountryCodeToName(String countryCode) {
		Locale location = new Locale("", countryCode);
		return location.getDisplayCountry().toUpperCase();
	}

	private String extractIconData(String dataPoint) {
		if (dataPoint.equals("01d")) {
			dataPoint = "clear_day";
		} else if (dataPoint.equals("01n")) {
			dataPoint = "clear_night";
		} else if (dataPoint.equals("02d")) {
			dataPoint = "cloudy_day";
		} else if (dataPoint.equals("02n")) {
			dataPoint = "cloudy_night";
		} else if (dataPoint.equals("03d") || dataPoint.equals("03n")) {
			dataPoint = "cloudy";
		} else if (dataPoint.equals("04d")) {
			dataPoint = "cloudy_day";
		} else if (dataPoint.equals("04n")) {
			dataPoint = "cloudy_night";
		} else if (dataPoint.equals("09d") || dataPoint.equals("09n")) {
			dataPoint = "raining";
		} else if (dataPoint.equals("10d")) {
			dataPoint = "rainy_day";
		} else if (dataPoint.equals("10n")) {
			dataPoint = "rainy_night";
		} else if (dataPoint.equals("11d") || dataPoint.equals("11n")) {
			dataPoint = "thunderstorm";
		} else if (dataPoint.equals("13d")) {
			dataPoint = "snowy_day";
		} else if (dataPoint.equals("13n")) {
			dataPoint = "snowy_night";
		} else if (dataPoint.equals("10n")) {
			dataPoint = "snowy_night";
		} else if (dataPoint.equals("50d") || dataPoint.equals("50n")) {
			dataPoint = "mist";
		}
		return dataPoint;
	}

	private static LocalDateTime convertUnixToHumanTime(String epochString) {
		long epochLong = Long.parseLong(epochString) * 1000;
		LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(epochLong), ZoneId.systemDefault());

		return date;
	}

	public String getWeatherDescription() {
		return weatherDescription;
	}

	public void setWeatherType(String weatherType) {
		this.weatherDescription = weatherType;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public int getCloudiness() {
		return cloudiness;
	}

	public void setCloudiness(int cloudiness) {
		this.cloudiness = cloudiness;
	}

	public int getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(int windSpeed) {
		this.windSpeed = windSpeed;
	}

	public int getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(int windDirection) {
		this.windDirection = windDirection;
	}

	public LocalDateTime getSunset() {
		return sunset;
	}

	public void setSunset(LocalDateTime sunset) {
		this.sunset = sunset;
	}

	public LocalDateTime getSunrise() {
		return sunrise;
	}

	public void setSunrise(LocalDateTime sunrise) {
		this.sunrise = sunrise;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getHumidity() {
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public LocalDateTime getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(LocalDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getWeatherIcon() {
		return weatherIcon;
	}

	public void setweatherIcon(String weatherIcon) {
		this.weatherIcon = weatherIcon;
	}
}
