package guiPackage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

import com.google.gson.JsonElement;

import dataHandlingPackage.DataRetriever;
import dataHandlingPackage.WeatherData;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WeatherGuiPane extends Application {

	public static String cityName = "Stockholm";
	public static boolean updatesStopped = true;
	public static Map<String, Object> namespace;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		startGui(primaryStage);
		setUpNamespace();
		
		JsonElement dataString = retrieveWeatherData();
		WeatherData weatherData = new WeatherData(dataString.getAsJsonObject());
		setGuiInformation(primaryStage.getScene(), weatherData);

		updatesStopped = false;

		Task task = new Task<Void>() {
			@Override
			public Void call() throws Exception {
				while (true) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							if (updatesStopped == false) {
								JsonElement dataString = retrieveWeatherData();
								WeatherData weatherData = new WeatherData(dataString.getAsJsonObject());
								setGuiInformation(primaryStage.getScene(), weatherData);
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
	
	private static JsonElement retrieveWeatherData() {
		DataRetriever dataRetriever = new DataRetriever();
		JsonElement response = dataRetriever.requestWeatherData(cityName, "weather");
		return response;
	}

	private void setGuiInformation(Scene scene, WeatherData weatherData) {

		setLabelText(scene, "#cityId", weatherData.getCity() + ",");
		setLabelText(scene, "#countryId", weatherData.getCountry());

		LocalDateTime lastUpdateDate = weatherData.getLastUpdated();
		String date = lastUpdateDate.getMonth() + " " + lastUpdateDate.getDayOfMonth() + ", "
				+ lastUpdateDate.getYear();
		setLabelText(scene, "#dateId", date);
		String time = lastUpdateDate.getDayOfWeek() + ", " + lastUpdateDate.getHour() + ":"
				+ lastUpdateDate.getMinute();
		setLabelText(scene, "#timeId", time);

		// temperature is cast to int because we don't want to show the .0 part
		// of the double value
		setLabelText(scene, "#temperatureId", weatherData.getTemperature() + "°");

		setImage(scene, "#weatherIconId", "weatherIcons/" + weatherData.getWeatherIcon() + ".png");
		setLabelText(scene, "#weatherDescriptionId", weatherData.getWeatherDescription());

		setImage(scene, "#humidityIconId", "indicatorIcons/humidity_indicator.png");
		setLabelText(scene, "#humidityValueId", weatherData.getHumidity() + "%");

		setImage(scene, "#windSpeedIconId", "indicatorIcons/wind_indicator.png");
		setLabelText(scene, "#windSpeedValueId", weatherData.getWindSpeed() + " m/s");

		setImage(scene, "#cloudinessIconId", "indicatorIcons/cloudiness_indicator.png");
		setLabelText(scene, "#cloudinessValueId", weatherData.getCloudiness() + "%");
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

		JsonElement dataString = retrieveWeatherData();
		WeatherData weatherData = new WeatherData(dataString.getAsJsonObject());
		setGuiInformation(parentPane.getScene(), weatherData);

		updatesStopped = false;
	}

	@FXML
	private void closeScreen(MouseEvent me) {
		Platform.exit();
	}
}
