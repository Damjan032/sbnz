package com.sbnz.adsys.model;

import org.kie.api.definition.type.Role;

import java.io.Serializable;

@org.kie.api.definition.type.Role(Role.Type.EVENT)
@org.kie.api.definition.type.Expires("1m")
public class MyEvent implements Serializable {
}
