package com.worldtrack.wtapi_android.models.unit;

import com.google.gson.JsonObject;
import com.worldtrack.wtapi_android.core.WialonObject;

/**
 * Worldtrack 02.09.15.
 */
public class CustomField extends WialonObject {

    public CustomField(JsonObject object) {
        super(object);
    }

    public String getName(){return jsonObject.get("n").getAsString();}

    public String getValue(){return jsonObject.get("v").getAsString();}

    public long getId(){return jsonObject.get("id").getAsLong();}
}
