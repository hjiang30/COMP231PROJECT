package guru.springframework.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;

import com.google.gson.Gson;

import guru.springframework.domain.Screener;
import guru.springframework.domain.StockInfo;
import guru.springframework.util.CSVReaderForInfo;

@Controller
public class IndexController {
	private static Map<String, List<String>> data = new HashMap<String, List<String>>();
	
	private List<StockInfo> dataset = new ArrayList<StockInfo>();
	
	private List<StockInfo> results = new ArrayList<StockInfo>();

	public IndexController() {
		
		dataset = CSVReaderForInfo.readStockInfo("data/");
		
		List<String> onlyClose = new ArrayList<String>();
		onlyClose.add("HII");
		onlyClose.add("COHR");
		
		
		List<String> onlyBoll = new ArrayList<String>();
		onlyBoll.add("IBM");
		onlyBoll.add("SO");
		onlyBoll.add("TJX");
		onlyBoll.add("UNP");
		onlyBoll.add("GE");
		onlyBoll.add("SNP");
		onlyBoll.add("NEE");
		onlyBoll.add("MFG");
		onlyBoll.add("GILD");

		List<String> onlyRSI = new ArrayList<String>();

		onlyRSI.add("CHA");
		onlyRSI.add("PTR");
		onlyRSI.add("TEVA");
		onlyRSI.add("OXY");

		List<String> onlyMACD = new ArrayList<String>();

		onlyMACD.add("FOX");
		onlyMACD.add("ORAN");

		List<String> onlyWilliam = new ArrayList<String>();
		onlyWilliam.add("STO");
		onlyWilliam.add("XOM");
		onlyWilliam.add("CVX");
		onlyWilliam.add("BP");
		onlyWilliam.add("DHR");
		onlyWilliam.add("BUD");
		onlyWilliam.add("ABT");
		onlyWilliam.add("HAL");
		onlyWilliam.add("INTC");
		onlyWilliam.add("AMZN");
		onlyWilliam.add("DD");
		onlyWilliam.add("KHC");
		onlyWilliam.add("UL");
		onlyWilliam.add("INFY");
		onlyWilliam.add("VOD");
		onlyWilliam.add("MMM");
		onlyWilliam.add("FOX");
		onlyWilliam.add("HDB");
		onlyWilliam.add("WBA");
		onlyWilliam.add("LLY");
		onlyWilliam.add("RELX");
		onlyWilliam.add("EOG");
		onlyWilliam.add("GSK");
		onlyWilliam.add("WBK");
		onlyWilliam.add("PUK");
		onlyWilliam.add("LMT");
		onlyWilliam.add("CMCSA");
		onlyWilliam.add("EPD");
		onlyWilliam.add("SLB");
		onlyWilliam.add("KMI");
		onlyWilliam.add("TOT");
		onlyWilliam.add("MDLZ");
		onlyWilliam.add("CELG");
		onlyWilliam.add("ASML");
		onlyWilliam.add("SNP");
		onlyWilliam.add("COP");
		onlyWilliam.add("MCK");
		onlyWilliam.add("ORAN");
		onlyWilliam.add("GOOGL");
		onlyWilliam.add("NVS");
		onlyWilliam.add("MTU");
		onlyWilliam.add("OXY");
		onlyWilliam.add("UTX");
		onlyWilliam.add("NGG");
		onlyWilliam.add("GIS");
		onlyWilliam.add("SAP");
		onlyWilliam.add("AVGO");

		// f2 = boll band;
		data.put("0100", onlyBoll);

		data.put("0010", onlyRSI);
		data.put("1000", onlyMACD);
		data.put("0001", onlyWilliam);
		data.put("9999", onlyClose);
	}

//	@RequestMapping("/")
//	String index() {
//		return "index";
//	}

	@RequestMapping(value = "/select", method = RequestMethod.POST)
	public @ResponseBody JSONArray rsi2(@RequestBody String what) {

		System.out.println("post  called! ");
		System.out.println(what);

		String choice = "" + what.substring(6, 7) + what.substring(13, 14) + what.substring(20, 21)
				+ what.substring(27, 28);
		System.out.println(choice);
		List<String> results;

		switch (choice) {
		case "1000":
			results = IndexController.data.get("1000");
			break;
		case "0010":
			results = IndexController.data.get("0010");
			break;
		case "0001":
			results = IndexController.data.get("0001");
			break;
		case "0100":
			results = IndexController.data.get("0100");
			break;
		default:
			results = IndexController.data.get("9999");
			break;

		}

		 Gson gson = new Gson();
		 //Map<String, Map<String,String>> data = new HashMap<String,Map<String,String>>();
		 Map<String,String> info = new  HashMap<String,String>();
		 info.put("name", "IBM Inc");
		 info.put("price", "100.0");
		 info.put("symbol", "IBM");
		// data.put("IBM", info);
		 //String json = gson.toJson(data);
		 //System.out.println(json);
		  
		 //return json;
		 List<Map<String,String>> results2  = new ArrayList<Map<String,String>>();
		 results2.add(info);

		JSONArray jsonAraay = new JSONArray(results);
		System.out.println(jsonAraay);
		return jsonAraay;
	}
	
	 @GetMapping("/")
	    public String greetingForm(Model model) {
	        model.addAttribute("screener", new Screener());
	        model.addAttribute("results", results);
	        return "index";
	    }
	 
	 @ModelAttribute("messages")
	    public List<String> messages() {
		 List<String> test = new ArrayList<String>();
		 test.add("test");
	        return test;
	    }
	
	
	@PostMapping("/screener")
    public String greetingSubmit(Model model,@ModelAttribute Screener screener) {
//		System.out.println(screener.getExchange());
//		System.out.println(screener.getSector());
//		System.out.println("called.");
		results = new ArrayList<StockInfo>();
		results = filterDataSet(screener);
		//System.out.println(results.size());
		return result(model);
        //return "results";
    }
	
	 @GetMapping("/result")
	    public String result(Model model) {
	        //model.addAttribute("results", results);
		 model.addAttribute("supertests", results);
	        return "results";
	    }
	 
	 @GetMapping("/plot")
	    public String plot(Model model) {
	        //model.addAttribute("results", results);
		 model.addAttribute("supertests", results);
	        return "plot";
	    }
	 
	 @PostMapping("/result")
	    public String resultPost(Model model) {
		 //model.addAttribute("supertests", results_);
		 System.out.println("button clicked");
		 System.out.println(results.size());
		 //results = new ArrayList<StockInfo>();
		 //TemplateEngine templateEngine = GTVGApplication.getTemplateEngine();
		 //model.addAttribute("results2", results);
		 model.addAttribute("screener", new Screener());
		 plot(model);
	        return "plot";
	    }
	
	
	
	
	
	public List<StockInfo> filterDataSet(Screener screener)
	{
		System.out.println("origin..");
		System.out.println(this.dataset.size());
		if(screener.getExchange() != null && !screener.getExchange().isEmpty())
		{
			System.out.println(screener.getExchange());
			this.dataset = this.dataset.stream().filter(stockinfo-> stockinfo.getExchange().equals(screener.getExchange())).collect(Collectors.toList());
		}
		
		if(screener.getSector() != null && !screener.getSector().isEmpty())
		{
			System.out.println(screener.getSector());
			this.dataset = this.dataset.stream().filter(stockinfo-> stockinfo.getSector().equals(screener.getSector())).collect(Collectors.toList());
		}
		
		
		if(screener.getPriceBiggerValue() != null && !screener.getPriceBiggerValue().isEmpty())
		{
			System.out.println(screener.getPriceBiggerValue());
			Double priceBiggerValue = Double.parseDouble(screener.getPriceBiggerValue());
			this.dataset = this.dataset.stream()
					.filter(stockinfo->stockinfo.getSharePrice()!=null)
					
					.filter(stockinfo-> Double.parseDouble(stockinfo.getSharePrice()) > priceBiggerValue ).collect(Collectors.toList());
		}
		
		
		if(screener.getPriceSmallerValue() != null && !screener.getPriceSmallerValue().isEmpty())
		{
			System.out.println(screener.getPriceSmallerValue());
			Double priceSmallerValue = Double.parseDouble(screener.getPriceSmallerValue());
			this.dataset = this.dataset.stream()
					.filter(stockinfo->stockinfo.getSharePrice()!=null)
					
					.filter(stockinfo-> Double.parseDouble(stockinfo.getSharePrice()) < priceSmallerValue ).collect(Collectors.toList());
		}
		
		if(screener.getMcBiggerThanValue() != null && !screener.getMcBiggerThanValue().isEmpty())
		{
			System.out.println(screener.getMcBiggerThanValue());
			Double mcBiggerThanValue = Double.parseDouble(screener.getMcBiggerThanValue());
			this.dataset = this.dataset.stream()
					.filter(stockinfo->stockinfo.getMarketCap()!=null)
					
					.filter(stockinfo-> stockinfo.getMarketCap() > mcBiggerThanValue ).collect(Collectors.toList());
		}
		
		if(screener.getMcSmallerThanValue()!= null && !screener.getMcSmallerThanValue().isEmpty())
		{
			System.out.println(screener.getMcSmallerThanValue());
			Double mcSmallerThanValue = Double.parseDouble(screener.getMcSmallerThanValue());
			this.dataset = this.dataset.stream()
					.filter(stockinfo->stockinfo.getMarketCap()!=null)
					
					.filter(stockinfo-> stockinfo.getMarketCap() < mcSmallerThanValue ).collect(Collectors.toList());
		}
		this.dataset.stream().forEach(stock->{System.out.println(stock.getName());});
		
		return this.dataset;
	}
	
	
}
