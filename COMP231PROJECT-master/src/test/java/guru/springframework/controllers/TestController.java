package guru.springframework.controllers;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import guru.springframework.domain.Screener;
import guru.springframework.domain.StockInfo;

public class TestController {

	@Test
	public void test() {
		IndexController c = new IndexController();
		
		Screener sc = new Screener();
		
		//sc.setExchange("New York");
		sc.setExchange("NASDAQ GS");
		sc.setSector("Consumer Discretionary");
		sc.setPriceBiggerValue("100");
		sc.setPriceSmallerValue("500");
		sc.setMcBiggerThanValue("10");
		sc.setMcSmallerThanValue("500");
		List<StockInfo> re = c.filterDataSet(sc);
		
		//System.out.println(re.get(0).getExchange());
		
		re.stream().forEach(stock->{System.out.println(stock.getName());});
		
		
	}

}
