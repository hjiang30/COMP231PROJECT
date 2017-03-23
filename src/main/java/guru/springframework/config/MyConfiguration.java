package guru.springframework.config;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import guru.springframework.services.CrossDownRule;
import guru.springframework.services.CrossRule;
import guru.springframework.services.CrossUpRule;
import guru.springframework.services.LargerThanRule;
import guru.springframework.services.LoadCSVService;
import guru.springframework.services.MathOperatorService;
import guru.springframework.services.RuleImplService;
import rx.Observable;

@Configuration
public class MyConfiguration {
	@Value("${rule.sma.n}")
	private int smaN1;

	@Bean
	public int getSMAN1() {
		return smaN1;
	}

	@Bean
	public CrossRule getCrossUPRule() {
		return new CrossUpRule();

	}

	@Bean
	public CrossRule getCrossDownRule() {
		return new CrossDownRule();

	}

	@Bean
	public LargerThanRule getLargerThanRule() {
		return new LargerThanRule();

	}

	@Bean
	public RuleImplService getRuleImplService() {
		return new RuleImplService();
	}

	@Bean
	public LoadCSVService getLoadCSVService() {
		LoadCSVService service = new LoadCSVService();

		service.setDataPath("data/histData/", "data/funData/");

		return service;
	}

	@Bean
	public MathOperatorService getMathOperatorService() {
		return new MathOperatorService();
	}

	@Bean
	public InputParameterSet getInputParameterSet() {
		return new InputParameterSet();
	}

	@Bean
	public Map<String, Map<String, Map<String, String>>> initData() {
		return new HashMap<String, Map<String, Map<String, String>>>();
	}


	@Bean
	public List<String> getSymbols() throws IOException {

		LoadCSVService service = new LoadCSVService();

		String path = "data/";
		
		return service.loadSymbols(path);
	}
	
	@Bean
	public DateFormat getDateFormat()
	{
		return new SimpleDateFormat("yyyy-MM-dd");
	}

	

}

