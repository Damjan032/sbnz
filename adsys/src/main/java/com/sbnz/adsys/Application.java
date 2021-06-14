package com.sbnz.adsys;

import com.sbnz.adsys.utils.MockDataGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Application {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Application.class, args);

		// Uncomment to populate database with test data
		// Configure by changing private constants in MockDataGenerator class
//		 new MockDataGenerator(context).generateData();
	}
}