package com.worldtrack.wtapi_android.models.resource;

import com.google.gson.JsonObject;
import com.worldtrack.wtapi_android.core.WialonObject;
import com.worldtrack.wtapi_android.dataformat.OrderStatus;

/**
 * Worldtrack 17.09.15.
 */
public class Order extends WialonObject {

    public Order(JsonObject object) {
        super(object);
    }
    public int getId()
    {
        return jsonObject.get("id").getAsInt();
    }
    public String getName()
    {
        return jsonObject.get("n").getAsString();
    }
    public JsonObject getCustomParams()
    {
        return jsonObject.get("p").getAsJsonObject();
    }
    public int getTimeFrom()
    {
        return jsonObject.get("tf").getAsInt();
    }
    public int getTimeTo()
    {
        return jsonObject.get("tt").getAsInt();
    }
    public int getUid()
    {
        return jsonObject.get("uid").getAsInt();
    }
    public double getLatitude()
    {
        return jsonObject.get("y").getAsDouble();
    }
    public double getLongitude()
    {
        return jsonObject.get("x").getAsDouble();
    }
    public long getUnitId()
    {
        return jsonObject.get("u").getAsLong();
    }

    public void setName(String name)
    {
        jsonObject.remove("n");
        jsonObject.addProperty("n",name);
    }
    public void setCustomParams(JsonObject customParams)
    {
        jsonObject.remove("p");
        jsonObject.add("p",customParams);

    }
    public void setTimeFrom(long unixTime)
    {
        jsonObject.remove("tf");
        jsonObject.addProperty("tf",unixTime);
    }
    public void setTimeTo(long unixTime)
    {
        jsonObject.remove("tt");
        jsonObject.addProperty("tt",unixTime);
    }
    public void setUid(int  uid)
    {
        jsonObject.remove("uid");
        jsonObject.addProperty("uid",uid);
    }
    public void setLatitude(double latitude)
    {
        jsonObject.remove("y");
        jsonObject.addProperty("y",latitude);
    }
    public void setLongitude(double longitude)
    {
        jsonObject.remove("x");
        jsonObject.addProperty("x",longitude);
    }
    public void setUnitId(long unitId)
    {
        jsonObject.remove("u");
        jsonObject.addProperty("u",unitId);
    }
    public OrderStatus getOrderStatus()
    {
        int status = jsonObject.get("s").getAsInt();
        for (OrderStatus st:OrderStatus.values())if(status==st.getType())return st;
        return null;
    }

}
//    {
//        "id":<uint>,	/* order id within resource */
//            "n":<text>,	/* order name */
//            "p":{		/* user-defined object content */
//        ...
//    },
//        "f":<bool>,	/* order flags: 1 - order would be marked as completed if there were at least one message within order area with zero speed in it */
//            "tf":<uint>,	/* lower bound of order completion time */
//            "tt":<uint>,	/* upper bound of order completion time  */
//            "trt":<uint>,   /* acceptable time of advancing the schedule, sec. */
//            "uid":<uint>,	/* unique id (is used as unique key in order history) */
//            "r":<uint>,	/* order point radius */
//            "y":<double>,	/* order point lattitude */
//        "x":<double>,	/* order point longitude */
//        "u":<long>,	/* unit id */
//        "s":<uint>,	/* order status: 0 - inactive (no unit assigned), 1 - active (unit assigned), 2 - completed in time, 3 - completed overdue, 4 - cancelled (not used) */
//            "st":<uint>	/* last status modification time */
//    }