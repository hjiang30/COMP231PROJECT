package guru.springframework.util;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

import guru.springframework.domain.StockInfo;

public class CSVReaderForInfo {
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	public static List<StockInfo> readStockInfo(String path)
	{
		List<StockInfo> result = new ArrayList<StockInfo>();
		String csvFile = path +  "Stock.csv";
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(csvFile));
			String[] line;
			while ((line = reader.readNext()) != null) {
				StockInfo data = new StockInfo();
				//System.out.println(line[0].split(" ")[0]);
				data.setSymbol(line[0].split(" ")[0]);
				data.setName(line[1]);
				data.setMarketCap(Double.valueOf(line[2])/1000000000);
				data.setExchange(line[4]);
				data.setSector(line[6]);
				result.add(data);


			}
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		System.out.println(result.size());
		
		System.out.println(result.get(0).getMarketCap());
		
		result.forEach(stock->{
			try {
				//System.out.println(stock.getSymbol());
				stock.openCSVandSetSharePrice();
				int len = stock.getPriceSeries().size();
				//System.out.println(len);
				if(len>0)
				{
					stock.setSharePrice(stock.getPriceSeries().get(len-1).getClosePrice());
				}
				
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		});
		System.out.println(result.get(0).getSharePrice());
		return result;
		
	}
	
	

}
