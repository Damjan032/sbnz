package com.sbnz.adsys.event;

import com.sbnz.adsys.model.Advertisement;
import com.sbnz.adsys.model.SocialMediaUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import java.util.Date;

@Role(Role.Type.EVENT)
@Expires("30s")
@Data
@AllArgsConstructor
public class AdvertisementClickEvent {
    private SocialMediaUser socialMediaUser;
    private Advertisement advertisement;
}
