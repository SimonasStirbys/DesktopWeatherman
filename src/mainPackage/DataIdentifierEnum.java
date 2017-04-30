package mainPackage;

public enum DataIdentifierEnum {
	WEATHER(new String[]{"weather", "description"}),
	TEMPERATURE(new String[]{"main", "temp"}),
	CLOUDINESS(new String[]{"clouds", "all"}),
	WIND_SPEED(new String[]{"wind", "speed"}),
	WIND_DIRECTION(new String[]{"weather", "description"}),
	SUNSET(new String[]{"sys", "sunset"}),
	SUNRISE(new String[]{"sys", "sunrise"}),
	CITY(new String[]{"name"}),
	COUNTRY(new String[]{"sys", "country"}),
	HUMIDITY(new String[]{"main", "humidity"}),
	ICON(new String[]{"weather", "icon"}),
	LAST_UPPDATED(new String[]{"dt"});
	
	public String[] dataIdentifier;
		
	private DataIdentifierEnum (String[] identifier){
		dataIdentifier = identifier;
	}
	
	public String[] getDataIdentifier(){
		return dataIdentifier;
	}	
}
