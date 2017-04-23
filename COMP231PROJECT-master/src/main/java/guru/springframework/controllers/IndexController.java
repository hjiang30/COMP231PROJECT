package guru.springframework.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
		
	}

	@PostMapping("/login")
	public String screener(Model model) {
		results = new ArrayList<StockInfo>();
        model.addAttribute("screener", new Screener());
        model.addAttribute("results", results);
        return "index";
    }
	@GetMapping("/test2")
	public String test(Model model) {
        //model.addAttribute("screener", new Screener());
        
        return "test";
    }
	
	@Value("${application.message:Hello World}")
	private String message;

//	@GetMapping("/testwelcome")
//	public String welcome(Model model) {
//		model.addAttribute("time", new Date());
//		model.addAttribute("message", this.message);
//		model.addAttribute("screener", new Screener());
//		return "index";
//	}
	

	
	 @GetMapping("/screener")
	    public String greetingForm(Model model) {
		    results = new ArrayList<StockInfo>();
	        model.addAttribute("screener", new Screener());
	        model.addAttribute("results", results);
	        return "index";
	    }
	 
//	 @ModelAttribute("messages")
//	    public List<String> messages() {
//		 List<String> test = new ArrayList<String>();
//		 test.add("test");
//	        return test;
//	    }
	
	
	@PostMapping("/screener")
    public String greetingSubmit(Model model,@ModelAttribute Screener screener) {
//		System.out.println(screener.getExchange());
//		System.out.println(screener.getSector());
		System.out.println(" @PostMapping(\"/screener\") called.");
		results = new ArrayList<StockInfo>();
		
		System.out.println("dataset size:");
		System.out.println(dataset.size());
		
		
		dataset = CSVReaderForInfo.readStockInfo("data/");
		
		results = filterDataSet(screener);
		
		System.out.println("result size:");
		System.out.println(results.size());
		
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
		 model.addAttribute("symbol", "GOOG");
	        return "plot2";
	    }
	 
//	 @GetMapping("/{file_name}")
//	 @ResponseBody
//	    public ResponseEntity<Resource>  data(@PathVariable("file_name") String fileName) {
//		 Resource file = storageService.loadAsResource(filename);
//	        return ResponseEntity
//	                .ok()
//	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
//	                .body(file);
//	    }
	 
	 @PostMapping("/result")
	    public String resultPost(Model model) {
		 //model.addAttribute("supertests", results_);
		 //System.out.println("button clicked");
		 //System.out.println(results.size());
		 //results = new ArrayList<StockInfo>();
		 //TemplateEngine templateEngine = GTVGApplication.getTemplateEngine();
		 //model.addAttribute("results2", results);
		 model.addAttribute("screener", new Screener());
		 results = new ArrayList<StockInfo>();
	     model.addAttribute("results", results);
	     return "index";
	    }
	 
	 @GetMapping("/quote")
	    public String greeting(@RequestParam(value="symbol", defaultValue="GOOG") String symbol,Model model) {
		 model.addAttribute("symbol",symbol );
		 //String html = symbol+"_candlestick_test2"
	        return symbol;
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
