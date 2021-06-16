package com.sbnz.adsys.config;

import com.sbnz.adsys.utils.Constants;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableAsync
public class KieConfig {

    @Bean
    public KieContainer kieContainer() {
        KieServices kieService = KieServices.Factory.get();
        KieContainer kieContainer = kieService.newKieContainer(kieService
                .newReleaseId(Constants.KIE_GROUP_ID, Constants.KIE_ARTIFACT_ID, Constants.KIE_VERSION));
        KieScanner kieScanner = kieService.newKieScanner(kieContainer);
        kieScanner.start(Constants.SCAN_INTERVAL);
        return kieContainer;
    }
    
    @Bean(name = "loginSession")
    public KieSession loginSession() {
        return kieContainer().newKieSession("login-session");
    }
    
    @Bean(name = "advertisementSession")
    public KieSession advertisementSession() {
        return kieContainer().newKieSession("advertisement-session");
    }

    @Bean(name = "recommendationSession")
    public KieSession recommendationSession() {
        return kieContainer().newKieSession("ksession-rules");
    }
    
}
