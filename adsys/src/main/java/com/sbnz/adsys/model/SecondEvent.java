package com.sbnz.adsys.model;

import lombok.AllArgsConstructor;
import org.kie.api.definition.type.Role;

import java.io.Serializable;

@org.kie.api.definition.type.Role(Role.Type.EVENT)
@org.kie.api.definition.type.Expires("1m")
public class SecondEvent implements Serializable {
}
