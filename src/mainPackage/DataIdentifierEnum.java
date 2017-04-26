package mainPackage;

public enum DataIdentifierEnum {
	WEATHER("\"description\":"),
	TEMPERATURE("\"temp\":"),
	CLOUDINESS("\"all\":"),
	WIND_SPEED("\"speed\":"),
	WIND_DIRECTION("\"deg\":"),
	SUNSET("\"sunset\":"),
	SUNRISE("\"sunrise\":"),
	CITY("\"name\":"),
	COUNTRY("\"country\":"),
	HUMIDITY("\"humidity\":"),
	ICON("\"icon\":"),
	LAST_UPPDATED("\"dt\":");
	
	public String dataIdentifier;
		
	private DataIdentifierEnum (String identifier){
		dataIdentifier = identifier;
	}
	
	public String getDataIdentifier(){
		return dataIdentifier;
	}	
}
