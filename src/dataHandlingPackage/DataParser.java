package dataHandlingPackage;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class DataParser {

	public String extractData(JsonObject weatherData, String[] path) {
		String dataPoint;
		if(path.length==1){
			dataPoint = weatherData.get(path[0]).getAsString();
		}else if(path[0].equals("weather")){
			dataPoint = extractFromJsonArray(weatherData, path);
		}else{
			dataPoint = extractFromJsonObject(weatherData, path);
		}
		return dataPoint.toUpperCase();
	}

	private String extractFromJsonObject(JsonObject jsonWeatherData, String[] jsonPath) {
		JsonObject jsonObject = jsonWeatherData.get(jsonPath[0]).getAsJsonObject();
		String dataValue = jsonObject.get(jsonPath[1]).getAsString();

		return dataValue;
	}
	
	private String extractFromJsonArray(JsonObject jsonWeatherData, String[] path) {
		JsonArray weatherArray = jsonWeatherData.get(path[0]).getAsJsonArray();
		String dataValue = weatherArray.get(0).getAsJsonObject().get(path[1]).getAsString();

		return dataValue;
	}
}
