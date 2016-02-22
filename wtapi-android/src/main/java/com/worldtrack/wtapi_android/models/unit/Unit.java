package com.worldtrack.wtapi_android.models.unit;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.worldtrack.wtapi_android.core.WialonObject;
import com.worldtrack.wtapi_android.models.resource.notification.Notification;

import java.util.ArrayList;
import java.util.Map;

/**
 * Worldtrack 19.08.15.
 */
public class Unit extends WialonObject {

    public Unit(JsonObject object) {
        super(object);
    }

    public Unit(Unit wialonObject) {
        super(wialonObject);
    }

    public UnitBasic getAsBasicFlagData()
    {
        return new UnitBasic(this.jsonObject);
    }
    public Sensor getAsSensorFlagData()
    {
        return new Sensor(this.jsonObject);
    }
    public AdvancedProperties getAsAdvansedProperties(){return new AdvancedProperties(this.jsonObject);}
    public CustomField[] getAsCustomFields(){
        JsonObject flds = jsonObject.get("flds").getAsJsonObject();
        CustomField[] fields = new CustomField[flds.entrySet().size()];
        int i = 0;
        for (Map.Entry<String,JsonElement> entry : flds.entrySet()) {
            JsonObject item = entry.getValue().getAsJsonObject();
            fields[i++] = new CustomField(item);
        }
        return fields;
    }
}
