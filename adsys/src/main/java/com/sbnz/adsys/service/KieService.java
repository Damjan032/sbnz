package com.sbnz.adsys.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Data
public class KieService {
    
    @Qualifier("loginSession")
    private final KieSession loginSession;
    
    
    private final KieContainer kieContainer;
    
    public KieSession getSession() {
        return this.kieContainer.newKieSession();
    }
    
    public KieSession getSession(String sessionName, String agendaGroup) {
        KieSession ks = this.kieContainer.newKieSession(sessionName);
        ks.getAgenda().getAgendaGroup(agendaGroup).setFocus();
        return ks;
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
