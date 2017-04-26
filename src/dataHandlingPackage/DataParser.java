package dataHandlingPackage;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;

public class DataParser {

	public String extractStringData(String data, String param) {
		if (data.contains(param)) {
						
			int startIndex = data.indexOf(param) + param.length()+1;
			int endIndex = data.indexOf('"', startIndex);
			String dataPoint = data.substring(startIndex, endIndex);
			
			if(param.equals("\"country\":")){
				dataPoint = convertCountryCodeToName(dataPoint);
			}
			
			return dataPoint.toUpperCase();
		} else {
			return "N/A";
		}
	}
	
	private String convertCountryCodeToName(String countryCode){
		Locale l = new Locale("", countryCode);
		return l.getDisplayCountry();
	}
	
	public Double extractDoubleData(String data, String param){		
		double dataPoint;
		if (data.contains(param)) {
			int startIndex = data.indexOf(param) + param.length();

			int endIndex = startIndex;
			while (data.charAt(endIndex) != ',' && data.charAt(endIndex) != '}') {
				endIndex++;
			}

			dataPoint = Double.parseDouble(data.substring(startIndex, endIndex));
			if(param.equals("\"temp\":")){
				dataPoint = Math.round (dataPoint * 1.0) / 1.0;  
			}
			
			return dataPoint;

		} else {
			return null;
		}
	}

	public LocalDateTime extractTimeData(String data, String param) {
		if (data.contains(param)) {
			int startIndex = data.indexOf(param) + param.length();

			int endIndex = startIndex;
			while (data.charAt(endIndex) != ',' && data.charAt(endIndex) != '}') {
				endIndex++;
			}

			String dataPoint = data.substring(startIndex, endIndex);
			return convertUnixToHumanTime(dataPoint);

		} else {
			return null;
		}
	}

	private static LocalDateTime convertUnixToHumanTime(String epochString) {
		long epochLong = Long.parseLong(epochString) * 1000;
		LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(epochLong), ZoneId.systemDefault());

		return date;
	}
		
	public String extractIconData(String data, String param) {
		if (data.contains(param)) {
						
			int startIndex = data.indexOf(param) + param.length()+1;
			int endIndex = data.indexOf('"', startIndex);
			String dataPoint = data.substring(startIndex, endIndex);
			
			if(dataPoint.equals("01d")){
				dataPoint = "clear_day";
			}else if(dataPoint.equals("01n")){
				dataPoint = "clear_night";
			}else if(dataPoint.equals("02d")){
				dataPoint = "cloudy_day";
			}else if(dataPoint.equals("02n")){
				dataPoint = "cloudy_night";
			}else if(dataPoint.equals("03d")||dataPoint.equals("03n")){
				dataPoint = "cloudy";
			}else if(dataPoint.equals("04d")){
				dataPoint = "cloudy_day";
			}else if(dataPoint.equals("04n")){
				dataPoint = "cloudy_night";
			}else if(dataPoint.equals("09d")||dataPoint.equals("09n")){
				dataPoint = "raining";
			}else if(dataPoint.equals("10d")){
				dataPoint = "rainy_day";
			}else if(dataPoint.equals("10n")){
					dataPoint = "rainy_night";
			}else if(dataPoint.equals("11d")||dataPoint.equals("11n")){
				dataPoint = "thunderstorm";
			}else if(dataPoint.equals("13d")){
				dataPoint = "snowy_day";
			}else if(dataPoint.equals("13n")){
					dataPoint = "snowy_night";
			}else if(dataPoint.equals("10n")){
				dataPoint = "snowy_night";
			}else if(dataPoint.equals("50d")||dataPoint.equals("50n")){
				dataPoint = "mist";
			}
			
			return dataPoint.toUpperCase();
		} else {
			return "N/A";
		}
	}


}
