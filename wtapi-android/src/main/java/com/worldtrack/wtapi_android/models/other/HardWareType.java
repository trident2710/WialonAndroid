package com.worldtrack.wtapi_android.models.other;

import com.google.gson.JsonObject;
import com.worldtrack.wtapi_android.core.WialonObject;

/**
 * Worldtrack 22.08.15.
 */
public class HardWareType extends WialonObject{

    public HardWareType(JsonObject object){
        super(object);
    }
    public HardWareType(HardWareType wialonObject){
        super(wialonObject);
    }
    public HardWareType(String name,long id)
    {
        jsonObject = new JsonObject();
        jsonObject.addProperty("id",id);
        jsonObject.addProperty("name",name);
    }

    public int getId()
    {
        return jsonObject.get("id").getAsInt();
    }

    public String getName()
    {
        return jsonObject.get("name").getAsString();
    }
}
