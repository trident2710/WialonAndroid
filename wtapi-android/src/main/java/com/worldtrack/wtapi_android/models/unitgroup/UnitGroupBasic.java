package com.worldtrack.wtapi_android.models.unitgroup;

import com.google.gson.JsonObject;
import com.worldtrack.wtapi_android.core.WialonObject;

/**
 * Worldtrack 19.08.15.
 */
public class UnitGroupBasic extends WialonObject{

    public UnitGroupBasic(JsonObject object) {
        super(object);
    }

    public UnitGroupBasic(UnitGroupBasic wialonObject) {
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
//        "nm":<text>,	/* name */
//            "cls":<uint>,	/* superclass ID: "avl_unit_group" */
//            "id":<uint>,	/* group ID */
//            "u":[<long>],	/* array of group units */
//        "uacl":<uint>	/* current user access level for unit group */
//    }
}
