package sbnz.advertisingsystem;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sbnz.advertisingsystem.utils.Constants;

@SpringBootApplication
public class AdvertisingSystemApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(AdvertisingSystemApplication.class, args);
    }

    
}
