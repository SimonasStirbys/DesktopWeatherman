package mainPackage;

import guiPackage.WeatherGuiPane;
import javafx.application.Application;

public class MainClass {
	// TODO: check for data differences in native vs english city names (e.g.
	// Götebog/Gothenburg)
	// TODO: figure out a user friendly way to search b/w same name cities in
	// two different countries (Melbourne Aus vs USA)
	// TODO: generate UI before getting data to avoid premature crash if
	// internet is slow
	// TODO: test the application with internet disabled.
	// TODO: data parcer can't return "N/A" for time data when it doesn't exist,
	// is setting it to null good?
	// information
	// TODO: show local last time of update, not user's system's time
	// TODO: methods for extracting data in data parser are a bit redundant, fix
	// it later.
	// TODO: meaning of bottom icons may not be clear enough, add clarfication
	// over mouseover
	public static void main(String[] args) {
		Application.launch(WeatherGuiPane.class, args);
	}
}
