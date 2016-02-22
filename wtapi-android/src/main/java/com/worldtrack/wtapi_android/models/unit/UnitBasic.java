package com.worldtrack.wtapi_android.models.unit;

import com.google.gson.JsonObject;
import com.worldtrack.wtapi_android.core.WialonObject;

/**
 * Worldtrack 19.08.15.
 */
public class UnitBasic extends WialonObject{
    public UnitBasic(JsonObject object) {
        super(object);
    }

    public UnitBasic(UnitBasic wialonObject) {
        super(wialonObject);
    }
    public int getId() {
        return jsonObject.get("id").getAsInt();
    }
    public void setId(int id)
    {
        jsonObject.remove("id");
        jsonObject.addProperty("id",id);
    }

    public String getName() {
        return jsonObject.get("nm").getAsString();
    }
    public void setName(String name)
    {
        jsonObject.remove("nm");
        jsonObject.addProperty("nm", name);
    }

//    {
//        "mu":<uint>,	/* measure units: 0 - si, 1 - us, 2 - imperial */
//            "nm":<text>,	/* name */
//            "cls":<uint>,	/* superclass ID: "avl_unit" */
//            "id":<uint>,	/* unit ID */
//            "uacl":<uint>	/* current user access level for unit */
//    }
}
