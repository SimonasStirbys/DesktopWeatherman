package guiPackage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

import com.google.gson.JsonElement;

import dataHandlingPackage.DataRetriever;
import dataHandlingPackage.WeatherData;
import dataHandlingPackage.WeatherDataForecast;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WeatherGuiPane extends Application {

	public static String cityName = "Stockholm";
	public static boolean updatesStopped = true;
	public static String screenView = "basic";
	public static Map<String, Object> namespace;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		startGui(primaryStage);
		setUpNamespace();
		
		JsonElement dataString = retrieveWeatherData("weather");
		WeatherData weatherData = new WeatherData(dataString.getAsJsonObject());
		dataString = retrieveWeatherData("forecast");
		WeatherDataForecast weatherDataForecast = new WeatherDataForecast(dataString);
		weatherDataForecast.setCurrentData(weatherData);
		setMainInformation(primaryStage.getScene(), weatherDataForecast);

		
		updatesStopped = false;

		Task task = new Task<Void>() {
			@Override
			public Void call() throws Exception {
				while (true) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							if (updatesStopped == false) {
								JsonElement dataString = retrieveWeatherData("weather");
								WeatherData weatherData = new WeatherData(dataString.getAsJsonObject());
								dataString = retrieveWeatherData("forecast");
								WeatherDataForecast weatherDataForecast = new WeatherDataForecast(dataString);
								weatherDataForecast.setCurrentData(weatherData);
								if(screenView.equals("basic")){
									setMainInformation(primaryStage.getScene(), weatherDataForecast);
								}else{
									setWeekViewInformation(primaryStage.getScene(), weatherDataForecast);
								}
							}
						}
					});
					Thread.sleep(5000);
				}
			}
		};
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
	}

	private void startGui(Stage stage) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/FXML/outline.fxml"));
			Scene scene = new Scene(root);
			scene.setFill(null);

			stage.setScene(scene);
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setUpNamespace() throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/outline.fxml"));
		Parent root = loader.load();
		namespace = loader.getNamespace();
	}
	
	private static JsonElement retrieveWeatherData(String apiType) {
		DataRetriever dataRetriever = new DataRetriever();
		JsonElement response = dataRetriever.requestWeatherData(cityName, apiType);
		return response;
	}

	private void setMainInformation(Scene scene, WeatherDataForecast weatherDataForecast) {

		WeatherData weatherData = weatherDataForecast.getCurrentData();
		
		setLabelText(scene, "#cityId", weatherDataForecast.getCity() + ",");
		setLabelText(scene, "#countryId", weatherDataForecast.getCountry());

		LocalDateTime lastUpdateDate = weatherData.getLastUpdated();
		String date = lastUpdateDate.getMonth() + " " + lastUpdateDate.getDayOfMonth() + ", "
				+ lastUpdateDate.getYear();
		setLabelText(scene, "#dateId", date);
		String time = lastUpdateDate.getDayOfWeek() + ", " + lastUpdateDate.getHour() + ":"
				+ lastUpdateDate.getMinute();
		setLabelText(scene, "#timeId", time);

		setLabelText(scene, "#temperatureId", weatherData.getTemperature() + "°");

		setImage(scene, "#weatherIconId", "weatherIcons/" + weatherData.getWeatherIcon() + ".png");
		setLabelText(scene, "#weatherDescriptionId", weatherData.getWeatherDescription());

		setLabelText(scene, "#humidityValueId", weatherData.getHumidity() + "%");

		setLabelText(scene, "#windSpeedValueId", weatherData.getWindSpeed() + " m/s");

		setLabelText(scene, "#cloudinessValueId", weatherData.getCloudiness() + "%");
	}
	
	private void setWeekViewInformation(Scene scene, WeatherDataForecast weatherDataForecast){
		
		WeatherData weatherData = weatherDataForecast.getCurrentData();
		int dayOfMonth = weatherData.getLastUpdated().getDayOfMonth();
		
		weatherData = weatherDataForecast.getDayHighestTemperatureData(dayOfMonth);
		setLabelText(scene, "#date0LabelId", weatherData.getLastUpdated().getDayOfWeek().toString().substring(0, 3));
		setImage(scene, "#day0ImageViewId", "weatherIcons/" + weatherData.getWeatherIcon() + ".png");
		setLabelText(scene, "#day0TemperatureLabelId", weatherData.getTemperature()+"°");
		
		weatherData = weatherDataForecast.getDayHighestTemperatureData(dayOfMonth+1);
		setLabelText(scene, "#date1LabelId", weatherData.getLastUpdated().getDayOfWeek().toString().substring(0, 3));
		setImage(scene, "#day1ImageViewId", "weatherIcons/" + weatherData.getWeatherIcon() + ".png");
		setLabelText(scene, "#day1TemperatureLabelId", weatherData.getTemperature()+"°");
		
		weatherData = weatherDataForecast.getDayHighestTemperatureData(dayOfMonth+2);
		setLabelText(scene, "#date2LabelId", weatherData.getLastUpdated().getDayOfWeek().toString().substring(0, 3));
		setImage(scene, "#day2ImageViewId", "weatherIcons/" + weatherData.getWeatherIcon() + ".png");
		setLabelText(scene, "#day2TemperatureLabelId", weatherData.getTemperature()+"°");
				
		weatherData = weatherDataForecast.getDayHighestTemperatureData(dayOfMonth+3);
		setLabelText(scene, "#date3LabelId", weatherData.getLastUpdated().getDayOfWeek().toString().substring(0, 3));
		setImage(scene, "#day3ImageViewId", "weatherIcons/" + weatherData.getWeatherIcon() + ".png");
		setLabelText(scene, "#day3TemperatureLabelId", weatherData.getTemperature()+"°");
		
		weatherData = weatherDataForecast.getDayHighestTemperatureData(dayOfMonth+4);
		setLabelText(scene, "#date4LabelId", weatherData.getLastUpdated().getDayOfWeek().toString().substring(0, 3));
		setImage(scene, "#day4ImageViewId", "weatherIcons/" + weatherData.getWeatherIcon() + ".png");
		setLabelText(scene, "#day4TemperatureLabelId", weatherData.getTemperature()+"°");
		
		weatherData = weatherDataForecast.getDayHighestTemperatureData(dayOfMonth+5);
		setLabelText(scene, "#date5LabelId", weatherData.getLastUpdated().getDayOfWeek().toString().substring(0, 3));
		setImage(scene, "#day5ImageViewId", "weatherIcons/" + weatherData.getWeatherIcon() + ".png");
		setLabelText(scene, "#day5TemperatureLabelId", weatherData.getTemperature()+"°");

	}

	private void setLabelText(Scene scene, String labelId, String text) {
		Label label = (Label) scene.lookup(labelId);
		label.setText(text);
	}

	private void setImage(Scene scene, String imageId, String imageFile) {
		ImageView imageView = (ImageView) scene.lookup(imageId);
		imageView.setImage(new Image(imageFile));
	}

	@FXML
	private void displayLocatonInputScreen(MouseEvent e) throws IOException {
		VBox pane = (VBox) e.getSource();
		Parent root = FXMLLoader.load(getClass().getResource("/FXML/location_input.fxml"));
		pane.getChildren().setAll(root);
		updatesStopped = true;
	}

	@FXML
	private void receiveNewLocationInput(ActionEvent ae) throws IOException {
		TextField textField = (TextField) ae.getSource();
		cityName = textField.getText();

		VBox parentPane = (VBox) textField.getParent();
		Parent root = FXMLLoader.load(getClass().getResource("/FXML/location_display.fxml"));
		parentPane.getChildren().setAll(root);

		JsonElement dataString = retrieveWeatherData("weather");
		WeatherData weatherData = new WeatherData(dataString.getAsJsonObject());
		dataString = retrieveWeatherData("forecast");
		WeatherDataForecast weatherDataForecast = new WeatherDataForecast(dataString);
		weatherDataForecast.setCurrentData(weatherData);
		setMainInformation(parentPane.getScene(), weatherDataForecast);

		updatesStopped = false;
	}
	
	@FXML
	private void toggleWeekView(MouseEvent me) throws IOException {
		screenView = "week";
		VBox vbox = (VBox) me.getSource();
		Scene scene = vbox.getScene();
		
		HBox centralPane = (HBox) scene.lookup("#centralPaneId");
		Parent weekDayPane = FXMLLoader.load(getClass().getResource("/FXML/week_day_layout.fxml"));
		centralPane.getChildren().setAll(weekDayPane);
		
		JsonElement dataString = retrieveWeatherData("weather");
		WeatherData weatherData = new WeatherData(dataString.getAsJsonObject());
		dataString = retrieveWeatherData("forecast");
		WeatherDataForecast weatherDataForecast = new WeatherDataForecast(dataString);
		weatherDataForecast.setCurrentData(weatherData);
		
		setWeekViewInformation(centralPane.getScene(), weatherDataForecast);
	}

	@FXML
	private void closeScreen(MouseEvent me) {
		Platform.exit();
	}
}
