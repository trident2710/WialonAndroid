package com.worldtrack.wtapi_android.models;

import com.google.gson.JsonObject;
import com.worldtrack.wtapi_android.core.WialonObject;

/**
 * Worldtrack 16.08.15.
 */
//@TODO: append with all fields
public class SessionData extends WialonObject {

    public SessionData(JsonObject object) {
        super(object);
    }

    public SessionData(WialonObject wialonObject) {
        super(wialonObject);
    }

    public String getSessionID() {
        return jsonObject.get("eid").getAsString();
    }

    public String getUserName() {
        return jsonObject.get("user").getAsJsonObject().get("nm").getAsString();
    }

    public long getUserId() {
        return jsonObject.get("user").getAsJsonObject().get("id").getAsLong();
    }
}
