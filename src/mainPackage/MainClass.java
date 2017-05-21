package mainPackage;

import java.io.IOException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dataHandlingPackage.DataRetriever;
import dataHandlingPackage.WeatherData;
import dataHandlingPackage.WeatherDataForecast;
import guiPackage.WeatherGuiPane;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

public class MainClass {
	// TODO: (major) implement a weather forecast for future days
	// TODO: a settings function allowing to change color and opacity of UI
	// TODO: figure out a user friendly way to search b/w same name cities in
	// two different countries (Melbourne Aus vs USA)
	// TODO: cancel function for new location input
	// TODO: generate UI before getting data to avoid premature crash if
	// internet is slow
	// TODO: test the application with internet disabled.
	// information
	// TODO: show local last time of update, not user's system's time
	// TODO: meaning of bottom icons may not be clear enough, add clarfication
	// over mouseover
	// TODO: make the window buttons more aesthethic.
	// TODO: retrieve the city and country for the UI from weatherDataForecast
	// now.

	// TODO: LocalDateTime has a built in function plus() that returns the given
	// date plus the amount of time. Use this to dynamically get weekly day
	// weather from current date

	public static void main(String[] args) throws IOException {
		Application.launch(WeatherGuiPane.class, args);
				
		String cityName = "Romelanda";
		DataRetriever dataRetriever = new DataRetriever();
		JsonElement response = dataRetriever.requestWeatherData(cityName, "weather");
		response = dataRetriever.requestWeatherData(cityName, "forecast");
		
		
		
	}
	
}
