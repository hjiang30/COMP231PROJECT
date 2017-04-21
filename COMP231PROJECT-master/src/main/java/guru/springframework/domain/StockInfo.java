package guru.springframework.domain;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.opencsv.CSVReader;

public class StockInfo {
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	public String symbol;
	
	public String name;
	
	public String sector;
	
	public Double marketCap;
	
	public String exchange;
	
	public String sharePrice;
	
	public List<Quote> priceSeries = new ArrayList<Quote>();

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public Double getMarketCap() {
		return marketCap;
	}

	public void setMarketCap(Double marketCap) {
		this.marketCap = marketCap;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public String getSharePrice() {
		return sharePrice;
	}

	public void setSharePrice(String sharePrice) {
		this.sharePrice = sharePrice;
	}
	
	public void openCSVandSetSharePrice() throws ParseException
	{
		
		String csvFile = "data/histData/" + this.symbol + ".csv";

		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(csvFile));
			String[] line;
			while ((line = reader.readNext()) != null) {
				Quote data = new Quote();
				DateTime t = new DateTime(DATE_FORMAT.parse(line[0]));
				data.setDateTime(t.toString());
				data.setAdjClosePrice(line[6]);
				data.setClosePrice(line[4]);
				data.setHighPrice(line[2]);
				data.setLowPrice(line[3]);
				data.setOpenPrice(line[1]);
				data.setVolume(line[5]);
				
				this.priceSeries.add(data);
				
				

				
			}
		} catch (IOException e) {
			//e.printStackTrace();
			
		}
		
	}

	public List<Quote> getPriceSeries() {
		return priceSeries;
	}

	public void setPriceSeries(List<Quote> priceSeries) {
		this.priceSeries = priceSeries;
	}

}
