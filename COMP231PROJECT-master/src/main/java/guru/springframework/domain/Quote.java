package guru.springframework.domain;

import org.joda.time.DateTime;

public class Quote {
	
	private DateTime t;
	
	private String openPrice;

	private String highPrice;

	private String lowPrice;

	private String closePrice;

	private String adjClosePrice;

	private String volume;

	public DateTime getT() {
		return t;
	}

	public void setT(DateTime t) {
		this.t = t;
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