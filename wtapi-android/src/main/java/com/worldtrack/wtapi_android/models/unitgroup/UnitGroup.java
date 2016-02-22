package com.worldtrack.wtapi_android.models.unitgroup;

import com.google.gson.JsonObject;
import com.worldtrack.wtapi_android.core.WialonObject;

/**
 * Worldtrack 19.08.15.
 */
public class UnitGroup extends WialonObject {
    public UnitGroup(JsonObject object) {
        super(object);
    }

    public UnitGroup(UnitGroup wialonObject) {
        super(wialonObject);
    }
    public UnitGroupBasic getAsUnitGroupBasicData()
    {
        return new UnitGroupBasic(this.jsonObject);
    }

}
