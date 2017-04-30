package dataHandlingPackage;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Retrieves data from the http://api.openweathermap.org API
 */
public class DataRetriever {

	
	public JsonElement requestWeatherData(String cityName, String apiType) {
		String targetSite = "http://api.openweathermap.org/data/2.5/";
		String measurementUnits = "metric";
		String appId = getAppId();
		String targetUrl = targetSite+apiType+"?q="+cityName+"&units="+measurementUnits +"&appid="+appId;

		HttpURLConnection connection = null;
		URL url;
		try {
			url = new URL(targetUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setUseCaches(false);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuffer response = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			System.out.println(response);
			
			JsonElement jsonElement = new JsonParser().parse(response.toString());
			return jsonElement;

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String getAppId(){
		String appId;
		try (BufferedReader br = new BufferedReader(new FileReader("src/appid.txt"))) {
			appId = br.readLine();
			return appId;
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
