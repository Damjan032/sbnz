package com.sbnz.adsys;

import com.sbnz.adsys.model.Advertisement;
import com.sbnz.adsys.unit.AdvertisementEventsCep;
import com.sbnz.adsys.unit.CalculateCoefficientRules;
import com.sbnz.adsys.unit.LoginRules;
import com.sbnz.adsys.unit.TargetGroupRules;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        TargetGroupRules.class,
        CalculateCoefficientRules.class,
        LoginRules.class,
        AdvertisementEventsCep.class
})
public class TestRunner {
}
