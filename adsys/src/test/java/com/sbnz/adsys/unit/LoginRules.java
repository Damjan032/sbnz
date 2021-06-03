package com.sbnz.adsys.unit;

import com.sbnz.adsys.event.LoginEvent;
import com.sbnz.adsys.model.User;
import org.droolsassert.DroolsAssert;
import org.droolsassert.DroolsSession;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Date;

@DroolsSession(resources = {
        "classpath*:/rules/login/login.drl"},
        ignoreRules = {"before", "after"},
        keepFactsHistory = false,
        logResources = true)
public class LoginRules {
    @Rule
    public DroolsAssert drools = new DroolsAssert();
    
    @Test
    public void testFirstSuccessfulLogin() {
        User user = createUser();
        
        insertEvent(user, 1);
        
        int numberOfRules = drools.fireAllRules();

        assertTrue(user.isEnabled());
        assertEquals(1, numberOfRules);
    }
    
    @Test
    public void testTwoUnsuccessfulLogin() {
        User user = createUser();
        user.setPassword("newPass");
        insertEvent(user, 2);
        
        int numberOfRules = drools.fireAllRules();
        
        assertTrue(user.isEnabled());
        assertEquals(2, numberOfRules);
    }
    
    @Test
    public void testEnabledIsFalseUnsuccessfulLoginMoreThen3Times() {
        User user = createUser();
        user.setPassword("newPass");
        insertEvent(user, 5);
        
        int numberOfRules = drools.fireAllRules();
        
        assertFalse(user.isEnabled());
        assertEquals(1, numberOfRules);
    }
    
    private void insertEvent(User user, int times) {
        for (int i = 0; i < times; i++) {
            drools.insert(new LoginEvent(new Date(), user,false));
        }
    }
    
    public static User createUser() {
        return new User(55L, "admin@mai.com", "admin123", "admin",
                "admin", null,  null, true);
    }
}
