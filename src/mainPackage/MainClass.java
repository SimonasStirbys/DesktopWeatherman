package mainPackage;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dataHandlingPackage.DataRetriever;
import dataHandlingPackage.WeatherData;
import guiPackage.WeatherGuiPane;
import javafx.application.Application;

public class MainClass {
	// TODO: figure out a user friendly way to search b/w same name cities in
	// two different countries (Melbourne Aus vs USA)
	// TODO: generate UI before getting data to avoid premature crash if
	// internet is slow
	// TODO: test the application with internet disabled.
	// information
	// TODO: show local last time of update, not user's system's time
	// TODO: meaning of bottom icons may not be clear enough, add clarfication
	// over mouseover
	// TODO: make the window buttons more aesthethic. 
	public static void main(String[] args) {
		Application.launch(WeatherGuiPane.class, args);
	}
}
