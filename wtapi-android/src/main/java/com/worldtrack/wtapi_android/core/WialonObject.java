package com.worldtrack.wtapi_android.core;

import com.google.gson.JsonObject;

/**
 * Worldtrack 19.08.15.
 */
public class WialonObject  {

    protected JsonObject jsonObject;
    protected WialonObject(){}
    public WialonObject(JsonObject object){
        this.jsonObject = object;
    }
    public WialonObject(WialonObject wialonObject){
        this.jsonObject = wialonObject.jsonObject;
    }

    public JsonObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JsonObject getValue(String value)
    {
        return jsonObject.get(value).getAsJsonObject();
    }
    @Override
    public String toString()
    {
        return jsonObject.toString();
    }
}
