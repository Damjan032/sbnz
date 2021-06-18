package com.sbnz.adsys.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
public class KieService {

    @Autowired
    private KieContainer kieContainer;

    @Autowired
    private SocialMediaUserService userService;

    private KieSession recommendationSession;
    private KieSession loginSession;
    private KieSession eventSession;

    @PostConstruct
    private void init() {
        recommendationSession = getSession("ksession-rules");
        loginSession = getSession("login-session");
        eventSession = getSession("advertisementEventsCep-session");
        if (userService == null) {
            throw new RuntimeException("User service je null");
        }
        eventSession.setGlobal("socialMediaUserService", userService);

        // TODO: mozda odavde da krene thread koji ce svakih x sekundi pokrenuti fireAllRules za eventSession?
    }


    public KieSession getSession() {
        return this.kieContainer.newKieSession();
    }

    public KieSession getSession(String sessionName, String agendaGroup) {
        KieSession ks = this.kieContainer.newKieSession(sessionName);
        ks.getAgenda().getAgendaGroup(agendaGroup).setFocus();
        return ks;
    }

    public KieSession getSession(String sessionName) {
        return this.kieContainer.newKieSession(sessionName);
    }

    public void runSession(KieSession kieSession) {
        kieSession.fireAllRules();
        kieSession.dispose();
        kieSession.destroy();
    }

    public KieSession createKieSessionFromDRL(String drl) {
        KieHelper kieHelper = new KieHelper();

        kieHelper.addContent(drl, ResourceType.DRL);

        return kieHelper.build().newKieSession();
    }
}
