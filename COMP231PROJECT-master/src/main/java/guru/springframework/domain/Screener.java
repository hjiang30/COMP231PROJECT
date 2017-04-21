package guru.springframework.domain;

public class Screener {
	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public String exchange;
	
	
	
	public String sector;
	
	public String priceBiggerValue;
	
	public String mcSmallerThanValue;
	
	public String mcBiggerThanValue;
	
	public String priceSmallerValue;

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getPriceBiggerValue() {
		return priceBiggerValue;
	}

	public void setPriceBiggerValue(String priceBiggerValue) {
		this.priceBiggerValue = priceBiggerValue;
	}

	public String getMcSmallerThanValue() {
		return mcSmallerThanValue;
	}

	public void setMcSmallerThanValue(String mcSmallerThanValue) {
		this.mcSmallerThanValue = mcSmallerThanValue;
	}

	public String getMcBiggerThanValue() {
		return mcBiggerThanValue;
	}

	public void setMcBiggerThanValue(String mcBiggerThanValue) {
		this.mcBiggerThanValue = mcBiggerThanValue;
	}

	public String getPriceSmallerValue() {
		return priceSmallerValue;
	}

	public void setPriceSmallerValue(String priceSmallerValue) {
		this.priceSmallerValue = priceSmallerValue;
	}
	
	

}
