package guru.springframework.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
public class IndexController {
	private static Map<String, List<String>> data = new HashMap<String, List<String>>();

	public IndexController() {
		
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

	@RequestMapping("/")
	String index() {
		return "index";
	}

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
}
