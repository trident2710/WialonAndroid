package com.worldtrack.wtapi_android.models.resource.notification;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.worldtrack.wtapi_android.core.WialonObject;

/**
 * Worldtrack 13.08.15.
 */
public class Notification extends WialonObject {


    public Notification(JsonObject object) {
        super(object);
    }

    public Notification(Notification wialonObject) {
        super(wialonObject);
    }

    public long getId() {
        return jsonObject.get("id").getAsLong();
    }
    public void setId(long id)
    {
        jsonObject.remove("id");
        jsonObject.addProperty("id",id);
    }

    public String getName() {
        return jsonObject.get("n").getAsString();
    }
    public void setName(String name)
    {
        jsonObject.remove("n");
        jsonObject.addProperty("n",name);
    }
    public long[] getUnitIds()
    {
        long[] res = new long[jsonObject.get("un").getAsJsonArray().size()];
        for (int i=0;i<res.length;i++)
        {
            res[i] = jsonObject.get("un").getAsJsonArray().get(i).getAsLong();
        }
        return res;
    }
    public void addUnit(long unitId)
    {
        (jsonObject.get("un").getAsJsonArray()).add(new JsonPrimitive(unitId));
    }


//    {
//        "unf":{
//        <text>:{	/* sequence number of notification */
//            "id":<long>,	/* ID */
//            "n":<text>,	/* name */
//                    "ta":<uint>,	/* activation time */
//                    "td":<uint>,	/* deactivation time */
//                    "ma":<uint>,	/* maximal alarms count (0 - unlimited) */
//                    "fl":<uint>,	/* notification types */
//                    "ac":<uint>,	/* alarms count */
//                    "un":[<long>],	/* array of units IDs */
//            "act":[<text>],	/* actions list */
//            "trg":<text>,	/* type of control */
//                    "crc":<long>	/* check sum of binary view of notification */
//        },
//        ...
//    },
//        "unfmax":<long>,	/* maximal count of notifications (-1 - unlimited) */
//    }

}
