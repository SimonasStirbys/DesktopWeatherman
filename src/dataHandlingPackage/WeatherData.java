package dataHandlingPackage;

import java.time.LocalDateTime;

import mainPackage.DataIdentifierEnum;

public class WeatherData {
	private String weatherDescription, city, country, weatherIcon;
	private double temperature, cloudiness, windSpeed, windDirection, humidity;
	private LocalDateTime sunset, sunrise, lastUpdated;

	public WeatherData(String dataString) {
		DataParser dataParser = new DataParser();

		this.setCity(dataParser.extractStringData(dataString, DataIdentifierEnum.CITY.getDataIdentifier()));
		this.setCountry(dataParser.extractStringData(dataString, DataIdentifierEnum.COUNTRY.getDataIdentifier()));
		this.setWeatherType(dataParser.extractStringData(dataString, DataIdentifierEnum.WEATHER.getDataIdentifier()));
		this.setTemperature(dataParser.extractDoubleData(dataString, DataIdentifierEnum.TEMPERATURE.getDataIdentifier()));
		this.setHumidity(dataParser.extractDoubleData(dataString, DataIdentifierEnum.HUMIDITY.getDataIdentifier()));
		this.setCloudiness(dataParser.extractDoubleData(dataString, DataIdentifierEnum.CLOUDINESS.getDataIdentifier()));
		this.setWindSpeed(dataParser.extractDoubleData(dataString, DataIdentifierEnum.WIND_SPEED.getDataIdentifier()));
		this.setSunrise(dataParser.extractTimeData(dataString, DataIdentifierEnum.SUNRISE.getDataIdentifier()));
		this.setSunset(dataParser.extractTimeData(dataString, DataIdentifierEnum.SUNSET.getDataIdentifier()));
		this.setweatherIcon(dataParser.extractIconData(dataString, DataIdentifierEnum.ICON.getDataIdentifier()));
		this.setLastUpdated(dataParser.extractTimeData(dataString, DataIdentifierEnum.LAST_UPPDATED.getDataIdentifier()));
	}

	public String getWeatherDescription() {
		return weatherDescription;
	}

	public void setWeatherType(String weatherType) {
		this.weatherDescription = weatherType;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public double getCloudiness() {
		return cloudiness;
	}

	public void setCloudiness(double cloudiness) {
		this.cloudiness = cloudiness;
	}

	public double getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
	}

	public double getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(double windDirection) {
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

	public double getHumidity() {
		return humidity;
	}

	public void setHumidity(double humidity) {
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
