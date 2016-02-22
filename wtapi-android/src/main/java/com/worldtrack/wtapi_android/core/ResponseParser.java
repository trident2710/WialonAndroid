package com.worldtrack.wtapi_android.core;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.worldtrack.wtapi_android.models.other.HardWareType;
import com.worldtrack.wtapi_android.models.other.WialonError;
import com.worldtrack.wtapi_android.models.resource.Order;
import com.worldtrack.wtapi_android.models.resource.driver.Driver;
import com.worldtrack.wtapi_android.models.resource.notification.DetailedNotification;
import com.worldtrack.wtapi_android.models.resource.notification.Notification;
import com.worldtrack.wtapi_android.models.unit.CustomField;
import com.worldtrack.wtapi_android.models.unit.Sensor;
import com.worldtrack.wtapi_android.models.unit.Unit;
import com.worldtrack.wtapi_android.models.unitgroup.UnitGroup;
import com.worldtrack.wtapi_android.models.SessionData;

import java.util.Map;

/**
 * Worldtrack 19.08.15.
 */
public class ResponseParser {

    public JsonElement parseJsonObjectFromString(String jsonString)
    {
        JsonParser parser  = new JsonParser();
        if(parser.parse(jsonString).isJsonObject()) return parser.parse(jsonString).getAsJsonObject();
        if(parser.parse(jsonString).isJsonArray()) return parser.parse(jsonString).getAsJsonArray();
        return parser.parse(jsonString).getAsJsonNull();
    }

    public WialonObject parseSensor(JsonElement o)
    {
        if(!isError(o)&&o.isJsonObject())
        {
            return new Sensor(o.getAsJsonObject());
        }
        else return new WialonError(o.getAsJsonObject());
    }

    public WialonObject parseUserResourceId(JsonElement o)
    {
        if(!isError(o)&&o.isJsonObject())
        {
            if(o.getAsJsonObject().has("items"))
            {
                return new WialonObject(o.getAsJsonObject().get("items").getAsJsonArray().get(0).getAsJsonObject());
            }
        }
        return new WialonError(o.getAsJsonObject());

    }

    public WialonObject[] parseSearchAsUnits(JsonElement o)
    {
        if(!isError(o)&&o.isJsonObject())
        {
            if(o.getAsJsonObject().has("items"))
            {
                JsonArray objects = o.getAsJsonObject().get("items").getAsJsonArray();

                Unit[] results = new Unit[objects.size()];
                for(int i = 0;i<objects.size();i++)
                {
                    results[i] = new Unit(objects.get(i).getAsJsonObject());
                }
                return results;
            }
        }
        return new WialonObject[]{new WialonError(o.getAsJsonObject())};
    }
    public WialonObject[] parseSearchAsOrders(JsonElement o)
    {
        if(!isError(o)&&o.isJsonObject())
        {
            if(o.getAsJsonObject().has("items"))
            {
                JsonArray objects = o.getAsJsonObject().get("items").getAsJsonArray();
                if(objects.get(0).getAsJsonObject().has("orders"))
                {
                    JsonObject object= objects.get(0).getAsJsonObject().get("orders").getAsJsonObject();
                    Log.d("entry set",""+object.entrySet().size());
                    Order[] orders = new Order[object.entrySet().size()];
                    int i= 0;
                    for (Map.Entry<String,JsonElement> entry : object.entrySet()) {
                        JsonObject order = entry.getValue().getAsJsonObject();
                        orders[i++] = new Order(order);
                    }
                    return orders;
                }

            }
        }
        return new WialonObject[]{new WialonError(o.getAsJsonObject())};
    }
    public WialonObject[] parseSearchAsUnitGroups(JsonElement o)
    {
        if(!isError(o)&&o.isJsonObject())
        {
            if(o.getAsJsonObject().has("items"))
            {
                JsonArray objects = o.getAsJsonObject().get("items").getAsJsonArray();

                UnitGroup[] results = new UnitGroup[objects.size()];
                for(int i = 0;i<objects.size();i++)
                {
                    results[i] = new UnitGroup(objects.get(i).getAsJsonObject());
                }
                return results;
            } else return new WialonObject[]{new WialonError(o.getAsJsonObject())};
        }
        return new WialonObject[]{new WialonError(o.getAsJsonObject())};


    }

    public WialonObject[] parseSearchAsNotifications(JsonElement o)
    {
        if(!isError(o)&&o.isJsonObject())
        {
            if(o.getAsJsonObject().has("items"))
            {
                JsonArray objects = o.getAsJsonObject().get("items").getAsJsonArray();
                if(objects.get(0).getAsJsonObject().has("unf"))
                {
                    JsonObject object= objects.get(0).getAsJsonObject().get("unf").getAsJsonObject();
                    Log.d("entry set",""+object.entrySet().size());
                    Notification[] notifications = new Notification[object.entrySet().size()];
                    int i= 0;
                    for (Map.Entry<String,JsonElement> entry : object.entrySet()) {
                        JsonObject notification = entry.getValue().getAsJsonObject();
                        notifications[i++] = new Notification(notification);
                    }
                    return notifications;
                }

            }
        }
        return new WialonObject[]{new WialonError(o.getAsJsonObject())};


    }

    public WialonObject[] parseSearchAsDrivers(JsonElement o)
    {
        if(!isError(o)&&o.isJsonObject())
        {
            if(o.getAsJsonObject().has("items"))
            {
                JsonArray objects = o.getAsJsonObject().get("items").getAsJsonArray();
                if(objects.get(0).getAsJsonObject().has("drvrs"))
                {
                    JsonObject object= objects.get(0).getAsJsonObject().get("drvrs").getAsJsonObject();

                    Driver[] drivers = new Driver[object.entrySet().size()];
                    int i= 0;
                    for (Map.Entry<String,JsonElement> entry : object.entrySet()) {
                        JsonObject driver = entry.getValue().getAsJsonObject();
                        drivers[i++] = new Driver(driver);
                    }
                    Log.d("drivers size:",""+drivers.length);
                    return drivers;
                }

            }
        }
        return new WialonObject[]{new WialonError(o.getAsJsonObject())};
    }

    public WialonObject parseSessionData(JsonElement o)
    {
        if(!isError(o)&&o.isJsonObject())
            return new SessionData(o.getAsJsonObject());
        return new WialonError(o.getAsJsonObject());
    }

    public WialonObject parseCreateUpdateResponseDriver(JsonElement o)
    {
        if(!isError(o)&&o.isJsonArray())
            return new Driver(o.getAsJsonArray().get(1).getAsJsonObject());
        return new WialonError(o.getAsJsonObject());
    }
    public WialonObject parseCreateUpdateOrder(JsonElement o)
    {
        if(!isError(o)&&o.isJsonArray())
            return new Order(o.getAsJsonArray().get(1).getAsJsonObject());
        return new WialonError(o.getAsJsonObject());
    }

    public WialonObject parseCreateUpdateResponseNotification(JsonElement o)
    {
        if(!isError(o)&&o.isJsonArray()){
            Log.d("Notification","started");
            return new Notification(o.getAsJsonArray().get(1).getAsJsonObject());
        }
        return new WialonError(o.getAsJsonObject());
    }

    public WialonObject parseCreateUpdateResponseUnit(JsonElement o)
    {
        if(!isError(o)&&o.isJsonObject())
            if(o.getAsJsonObject().has("item"))
                return new Unit(o.getAsJsonObject().get("item").getAsJsonObject());
        return new WialonError(o.getAsJsonObject());
    }

    public WialonObject parseCreateUpdateResponseUnitGroup(JsonElement o)
    {
        if(!isError(o)&&o.isJsonObject())
            if(o.getAsJsonObject().has("item"))
                return new UnitGroup(o.getAsJsonObject().get("item").getAsJsonObject());
        return new WialonError(o.getAsJsonObject());
    }

    public WialonObject parseDeleteResponse(JsonElement o)
    {
        if(!isError(o))
        {
            if(o.isJsonObject())
                return new WialonObject(o.getAsJsonObject());
            else return new WialonObject();
        }

        return new WialonError(o.getAsJsonObject());
    }

    public WialonObject[] parseHardwareTypesResponse(JsonElement o)
    {
        if(!isError(o)&&o.isJsonArray())
        {
            HardWareType[] list = new HardWareType[o.getAsJsonArray().size()];
            if(list.length!=0) {
                for (int i = 0; i < list.length; i++) {
                    list[i] = new HardWareType(o.getAsJsonArray().get(i).getAsJsonObject());
                }
                return list;
            }
        }
        return new WialonObject[]{new WialonError(o.getAsJsonObject())};

    }
    public WialonObject parseCustomFieldResponse(JsonElement o)
    {
        if(!isError(o)&&o.isJsonArray())
        {
            if(o.getAsJsonArray().size()>=1&&o.getAsJsonArray().get(1).isJsonObject())
                return new CustomField(o.getAsJsonArray().get(1).getAsJsonObject());
        }
        return new WialonError(o.getAsJsonObject());

    }

    public WialonObject parseUploadFileResponse(JsonElement o)
    {
        if(!isError(o)&&o.isJsonObject())
        {
            Log.d("image",o.getAsJsonObject().toString());
            return new WialonObject(o.getAsJsonObject());
        }
        return new WialonError(o.getAsJsonObject());
    }

    private boolean isError(JsonElement element)
    {
        if(element.isJsonObject())
        {
            if(element.getAsJsonObject().has("error"))
            {
                return true;
            }
        }
        return false;
    }

    public WialonObject parseUpdateHardwareType(JsonElement o) {
        return parseDeleteResponse(o);
    }

    public WialonObject[] parseDetailedNotifications(JsonElement o)
    {
        if(!isError(o)&&o.isJsonArray())
        {
            DetailedNotification[] list = new DetailedNotification[o.getAsJsonArray().size()];
            if(list.length!=0) {
                for (int i = 0; i < list.length; i++) {
                    list[i] = new DetailedNotification(o.getAsJsonArray().get(i).getAsJsonObject());
                }
                return list;
            }
        }
        return new WialonObject[]{new WialonError(o.getAsJsonObject())};
    }

}
