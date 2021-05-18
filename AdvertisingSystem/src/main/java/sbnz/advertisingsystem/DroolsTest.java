package sbnz.advertisingsystem;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTest {

    public static void main(String[] args) {
        System.out.println("I got here");
        System.out.println("");
        try {
            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();
            KieSession kSession = kContainer.newKieSession("ksession-rules");

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
