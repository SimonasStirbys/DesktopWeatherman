package guiPackage;

import java.io.IOException;
import java.time.LocalDateTime;

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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WeatherGuiPane extends Application {

	public static String cityName = "Stockholm";
	public static boolean updatesStopped = true;

	@Override
	public void start(Stage primaryStage) throws Exception {
		startGui(primaryStage);
		String dataString = retrieveWeatherData();
		WeatherData weatherData = new WeatherData(dataString);
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
								String dataString = retrieveWeatherData();
								WeatherData weatherData = new WeatherData(dataString);
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

			stage.setScene(scene);
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String retrieveWeatherData() {
		DataRetriever dataRetriever = new DataRetriever();
		String response = dataRetriever.retrieveCurrentWeather(cityName);
		return response;
	}

	private void setGuiInformation(Scene scene, WeatherData weatherData) {

		Label cityLabel = (Label) scene.lookup("#cityId");
		cityLabel.setText(weatherData.getCity() + ",");
		Label countryLabel = (Label) scene.lookup("#countryId");
		countryLabel.setText(weatherData.getCountry());

		LocalDateTime lastUpdateDate = weatherData.getLastUpdated();
		Label dateLabel = (Label) scene.lookup("#dateId");
		dateLabel.setText(
				lastUpdateDate.getMonth() + " " + lastUpdateDate.getDayOfMonth() + ", " + lastUpdateDate.getYear());
		Label timeLabel = (Label) scene.lookup("#timeId");
		timeLabel.setText(
				lastUpdateDate.getDayOfWeek() + ", " + lastUpdateDate.getHour() + ":" + lastUpdateDate.getMinute());

		Label temperatureLabel = (Label) scene.lookup("#temperatureId");
		// temperature is cast to int because we don't want to show the .0 part
		// of the double value
		temperatureLabel.setText((int) weatherData.getTemperature() + "°");
		ImageView weatherIcon = (ImageView) scene.lookup("#weatherIconId");
		weatherIcon.setImage(new Image("weatherIcons/" + weatherData.getWeatherIcon() + ".png"));
		Label weatherDescriptionLabel = (Label) scene.lookup("#weatherDescriptionId");
		weatherDescriptionLabel.setText(weatherData.getWeatherDescription());

		ImageView humidityIcon = (ImageView) scene.lookup("#humidityIconId");
		humidityIcon.setImage(new Image("indicatorIcons/humidity_indicator.png"));
		Label humidityLabel = (Label) scene.lookup("#humidityValueId");
		humidityLabel.setText(weatherData.getHumidity() + "%");

		ImageView windSpeedIcon = (ImageView) scene.lookup("#windSpeedIconId");
		windSpeedIcon.setImage(new Image("indicatorIcons/wind_indicator.png"));
		Label windSpeedLabel = (Label) scene.lookup("#windSpeedValueId");
		windSpeedLabel.setText(weatherData.getWindSpeed() + "m/s");

		ImageView cloudinessIcon = (ImageView) scene.lookup("#cloudinessIconId");
		cloudinessIcon.setImage(new Image("indicatorIcons/cloudiness_indicator.png"));
		Label cloudinessLabel = (Label) scene.lookup("#cloudinessValueId");
		cloudinessLabel.setText(weatherData.getCloudiness() + "%");
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

		String dataString = retrieveWeatherData();
		WeatherData weatherData = new WeatherData(dataString);
		setGuiInformation(parentPane.getScene(), weatherData);

		updatesStopped = false;
	}
}
