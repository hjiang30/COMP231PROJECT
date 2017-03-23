package guru.springframework;

import java.text.DateFormat;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.config.InputParameterSet;
import guru.springframework.config.MyConfiguration;
import guru.springframework.services.CrossRule;
import guru.springframework.services.LargerThanRule;
import guru.springframework.services.LoadCSVService;
import guru.springframework.services.MathOperatorService;
import guru.springframework.services.RuleImplService;



@SpringBootApplication
@Import(MyConfiguration.class)
@RestController
public class SpringBootWebApplication {

	@Autowired
	private CrossRule getCrossUPRule;

	@Autowired
	private CrossRule getCrossDownRule;

	@Autowired
	private RuleImplService getRuleImplService;

	@Autowired
	private LoadCSVService getLoadCSVService;

	@Autowired
	private MathOperatorService getMathOperatorService;

	@Autowired
	private InputParameterSet getInputParameterSet;

	@Autowired
	private Map<String, Map<String, Map<String, String>>> initData;

//	@Autowired
//	private DateTimeFormatter getDateTimeFormatter;

	@Autowired
	private LargerThanRule getLargerThanRule;

	@Autowired
	private List<String> getSymbols;

	@Autowired
	private DateFormat getDateFormat;

	// main computation method for the application.
	@Bean
	InitializingBean computeAndSaveIntoDatabase() {
		return null;
	};

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebApplication.class, args);
	}
}
