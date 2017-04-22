package guru.springframework;




import org.springframework.beans.factory.InitializingBean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.web.bind.annotation.RestController;







@SpringBootApplication

@RestController
public class SpringBootWebApplication {



	
	@Bean
	InitializingBean computeAndSaveIntoDatabase() {
		return () -> {
//			
	};
	};

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebApplication.class, args);
	}
}
