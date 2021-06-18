package com.sbnz.adsys;

import com.sbnz.adsys.model.*;
import org.drools.core.ClockType;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.api.time.SessionPseudoClock;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

public class DroolsTest {

    public static KieSession getSession(boolean pseudo) {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.newKieClasspathContainer();
        KieSessionConfiguration ksconf = ks.newKieSessionConfiguration();
        if (pseudo)
            ksconf.setOption(ClockTypeOption.get(ClockType.PSEUDO_CLOCK.getId()));
        KieSession kSession = kc.newKieSession("test-rules", ksconf);
        return kSession;
    }

    public static void main(String[] args) {
        try {
            // load up the knowledge base

            KieSession kSession = getSession(true);
            SessionPseudoClock clock = kSession.getSessionClock();

            // go !
            MyEvent e1 = new MyEvent();
            SecondEvent e2 = new SecondEvent();

            kSession.insert(e1);
            clock.advanceTime(50, TimeUnit.SECONDS);
            kSession.insert(e2);
            kSession.fireAllRules();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
