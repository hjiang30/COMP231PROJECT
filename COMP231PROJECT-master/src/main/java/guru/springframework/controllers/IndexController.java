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
    @RequestMapping("/")
    String index(){
        return "index";
    }
    
    
    
    @RequestMapping(value="/test2",method = RequestMethod.POST)
    public @ResponseBody JSONArray rsi2(@RequestBody String what) {
    	
    	System.out.println("post  called! ");
    	System.out.println(what);
		Gson gson = new Gson();
		Map<String, String> data = new HashMap<String,String>();
		data.put("test", "??");
		String json = gson.toJson(data);
		
		List<String> results = new ArrayList<String>();
		results.add("G");
		results.add("A");
		JSONArray jsonAraay = new JSONArray(results);
		return jsonAraay;
	}
}
