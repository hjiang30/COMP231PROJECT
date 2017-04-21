package guru.springframework.domain;

import org.joda.time.DateTime;

public class Quote {
	
	public String dateTime;
	
	public String openPrice;

	public String highPrice;

	public String lowPrice;

	public String closePrice;

	public String adjClosePrice;

	public String volume;



	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(String openPrice) {
		this.openPrice = openPrice;
	}

	public String getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(String highPrice) {
		this.highPrice = highPrice;
	}

	public String getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(String lowPrice) {
		this.lowPrice = lowPrice;
	}

	public String getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(String closePrice) {
		this.closePrice = closePrice;
	}

	public String getAdjClosePrice() {
		return adjClosePrice;
	}

	public void setAdjClosePrice(String adjClosePrice) {
		this.adjClosePrice = adjClosePrice;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

}


//DateTime t = new DateTime(DATE_FORMAT.parse(line[0]));
//data.setDate(t);
//data.setAdjClosePrice(line[6]);
//data.setClosePrice(line[4]);
//data.setHighPrice(line[2]);
//data.setLowPrice(line[3]);
//data.setOpenPrice(line[1]);
//data.setSymbol(symbol);
//data.setVolume(line[5]);