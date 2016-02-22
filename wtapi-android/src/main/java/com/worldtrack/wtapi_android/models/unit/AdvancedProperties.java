package com.worldtrack.wtapi_android.models.unit;

import com.google.gson.JsonObject;
import com.worldtrack.wtapi_android.core.WialonObject;

/**
 * Worldtrack 22.08.15.
 */
public class AdvancedProperties extends WialonObject {

    public AdvancedProperties(JsonObject object) {
        super(object);
    }

    public AdvancedProperties(Sensor wialonObject) {
        super(wialonObject);
    }

    public long getHardwareType()
    {
        return jsonObject.get("hw").getAsLong();
    }

    public String getHardwareUniqueId()
    {
        return jsonObject.get("uid").getAsString();
    }

//    {
//        "uid":<text>,	/* unique ID (hardware) */
//        "hw":<long>,	/* hardware type */
//        "ph":<text>,	/* phone number */
//        "ph2":<text>,	/* second phone number */
//        "psw":<text>	/* password */
//    }

}
