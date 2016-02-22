package com.worldtrack.wtapi_android.core;

import com.fasterxml.jackson.core.JsonParser;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.worldtrack.wtapi_android.dataformat.ActionType;
import com.worldtrack.wtapi_android.dataformat.HWFilter;
import com.worldtrack.wtapi_android.dataformat.OrderActionType;
import com.worldtrack.wtapi_android.dataformat.ResourceDataFlag;
import com.worldtrack.wtapi_android.dataformat.UnitDataFlag;
import com.worldtrack.wtapi_android.dataformat.UnitGroupDataFlag;
import com.worldtrack.wtapi_android.models.other.HardWareType;
import com.worldtrack.wtapi_android.models.resource.Order;
import com.worldtrack.wtapi_android.models.resource.driver.Driver;
import com.worldtrack.wtapi_android.models.resource.notification.DetailedNotification;
import com.worldtrack.wtapi_android.models.unit.Sensor;
import com.worldtrack.wtapi_android.models.unit.Unit;
import com.worldtrack.wtapi_android.search.SearchSpecification;

/**
 * Worldtrack 19.08.15.
 */
public class RequestBuilder {

    public static String base = "https://hst-api.wialon.com/wialon/ajax.html?";

    public String[] buildTokenLoginRequest(String token)
    {
        String[] request = new String[2];
        request[0] = base+"svc=token/login&params={params}";
        request[1] = "{\"token\":\""+token+"\"}";
        return request;
    }
    public String[] buildSearchRequest(SearchSpecification specification,long dataFlags,String sessionId)
    {
        String s = "{\"spec\":{\"itemsType\":\""+specification.getElementType()+"\","+
                "\"propName\":\""+specification.getItemProperty()+"\"," +
                "\"propValueMask\":\""+specification.getValueMask()+"\"," +
                "\"sortType\":\""+specification.getSortType()+"\"}," +
                "\"force\":1,\"flags\":"+dataFlags+",\"from\":0,\"to\":0 }";

        String[] request = new String[3];
        request[0] = base+"svc=core/search_items&params={params}&sid={sid}";
        request[1] = s;
        request[2] = sessionId;
        return request;
    }
    public String[] buildDriverCRUDRequest(String sessionId,ActionType actionType,long creatorId,Driver driver)
    {
        String s = "";
        s = "{\"itemId\":\""+creatorId+",";
        if(actionType == ActionType.DELETE || actionType == ActionType.UPDATE)
        {
                    s+="\"id\":0,";
        } else s+="\"id\":"+driver.getId()+",";
        s+="\"callMode\":\""+actionType.getValue()+"\"";
        if(actionType == ActionType.DELETE)
        {
            s+="}";
        } else
        {
            s+=",";
            s+="\"c\":\""+driver.getCode()+"\",";
            s+="\"ds\":\""+driver.getDescription()+"\",";
            s+="\"n\":\""+driver.getName()+"\",";
            s+="\"p\":\""+driver.getPhoneNumber()+"\"}";

        }
        String[] request = new String[3];
        request[0] = base+"svc=resource/update_driver&params={params}&sid={sid}";
        request[1] = s;
        request[2] = sessionId;
        return request;
    }

    public String[] buildNotificationCRUDRequest(String sessionId,ActionType actionType,long creatorId,DetailedNotification notification)
    {
        JsonObject object;
        if(actionType==ActionType.DELETE)
        {
            object = new JsonObject();
            object.addProperty("id",notification.getId());
        } else object = notification.getJsonObject();

        if(actionType==ActionType.CREATE)
        {
            object.remove("id");
            object.addProperty("id",0);
        }
        object.addProperty("callMode",actionType.getValue());
        object.addProperty("itemId",creatorId);


        String[] request = new String[3];
        request[0] = base+"svc=resource/update_notification&params={params}&sid={sid}";
        request[1] = object.toString();
        request[2] = sessionId;
        return request;
    }
    public String[] buildCreateUnitRequest(String sessionId,long creatorId,Unit unit,long flag)
    {

        long hw = unit.getAsAdvansedProperties().getHardwareType();

        JsonObject object = new JsonObject();
        object.addProperty("creatorId",creatorId);
        object.addProperty("name",unit.getAsBasicFlagData().getName());
        object.addProperty("hwTypeId",hw);
        object.addProperty("dataFlags",flag);

        String[] request = new String[3];
        request[0] = base+"svc=core/create_unit&params={params}&sid={sid}";
        request[1] = object.toString();
        request[2] = sessionId;
        return request;
    }

    public String[] buildCreateUnitGroupRequest(String sessionId,long creatorId,String name,long flag)
    {

        JsonObject object = new JsonObject();
        object.addProperty("creatorId",creatorId);
        object.addProperty("name",name);
        object.addProperty("dataFlags",flag);

        String[] request = new String[3];
        request[0] = base+"svc=core/create_unit_group&params={params}&sid={sid}";
        request[1] = object.toString();
        request[2] = sessionId;
        return request;
    }

    public String[] buildGetHwTypesRequest(String sessionId,HWFilter filterType,Object filterValue,boolean includeType)
    {
        String s = "{\"filterType\":\""+filterType.getValue()+"\","+
                "\"filterValue\":[\""+filterValue+"\"],"+
                "\"includeType\":"+includeType+"}";

        String[] request = new String[3];
        request[0] = base+"svc=core/get_hw_types&params={params}&sid={sid}";
        request[1] = s;
        request[2] = sessionId;
        return request;
    }
    public String[] buildGetHwTypesRequest(String sessionId,HWFilter filterType,HWFilter filterValue,boolean includeType)
    {
        return buildGetHwTypesRequest(sessionId,filterType,filterValue.getValue(),includeType);
    }

    public String[] buildDeleteItem(String sessionId,long itemId)
    {
        String s = "{\"itemId\":"+itemId+"}";

        String[] request = new String[3];
        request[0] = base+"svc=item/delete_item&params={params}&sid={sid}";
        request[1] = s;
        request[2] = sessionId;
        return request;
    }

    public String[] buildCRUDSensorRequest(String sessionId,ActionType actionType,long creatorId,Sensor sensor)
    {
        JsonObject object;
        if(actionType==ActionType.DELETE)
        {
            object = new JsonObject();
            object.addProperty("id",sensor.getId());
        } else object = sensor.getJsonObject();

        if(actionType==ActionType.CREATE)
        {
            object.remove("id");
            object.addProperty("id",0);
        }
        object.addProperty("callMode",actionType.getValue());
        object.addProperty("itemId",creatorId);

        String[] request = new String[3];
        request[0] = base+"svc=item/delete_item&params={params}&sid={sid}";
        request[1] = object.toString();
        request[2] = sessionId;
        return request;

    }

    public String[] buildGetUserResourceId(String sessionId)
    {
        String s = "{\"spec\":{\"itemsType\":\""+ SearchSpecification.ElementType.resource.getValue()+"\","+
                "\"propName\":\""+ SearchSpecification.ItemProperty.name.getValue()+"\"," +
                "\"propValueMask\":\""+"*"+"\"," +
                "\"sortType\":\""+SearchSpecification.ItemProperty.name.getValue()+"\"}," +
                "\"force\":1,\"flags\":"+ ResourceDataFlag.baseFlag.getValue()+",\"from\":0,\"to\":0 }";

        String[] request = new String[3];
        request[0] = base+"svc=core/search_items&params={params}&sid={sid}";
        request[1] = s;
        request[2] = sessionId;
        return request;
    }

    public String[] buildCustomFieldAction(ActionType actionType,String sessionId,String name,String value,long itemId,long id)
    {
        JsonObject object = new JsonObject();
        object.addProperty("itemId", itemId);
        object.addProperty("callMode",actionType.getValue());
        object.addProperty("id",(actionType==ActionType.DELETE||actionType==ActionType.UPDATE)?id:0);
        if(actionType==ActionType.CREATE||actionType==ActionType.UPDATE)
        {
            object.addProperty("n", name);
            object.addProperty("v", value);
        }

        String[] request = new String[3];
        request[0] = base+"svc=item/update_custom_field&params={params}&sid={sid}";
        request[1] = object.toString();
        request[2] = sessionId;
        return request;
    }

    public String[] buildUploadFileRequest(long itemId,String sessionId)
    {
        JsonObject object = new JsonObject();
        object.addProperty("itemId", itemId);
        String[] request = new String[3];
        request[0] = base+"svc=unit/upload_image&params={params}&sid={sid}";
        request[1] = object.toString();
        request[2] = sessionId;
        return request;
    }

    public String[] buildDeleteNotificationRequest(long itemId,long notificationId,String sessionId)
    {
        JsonObject object = new JsonObject();
        object.addProperty("itemId", itemId);
        object.addProperty("id", notificationId);
        object.addProperty("callMode", "delete");
        String[] request = new String[3];
        request[0] = base+"svc=resource/update_notification&params={params}&sid={sid}";
        request[1] = object.toString();
        request[2] = sessionId;
        return request;
    }

    public String[] buildUpdateHwTypeRequest(long unitId,long hwId,String uniqueId,String sessionId)
    {
        JsonObject object = new JsonObject();
        object.addProperty("itemId",unitId);
        object.addProperty("deviceTypeId",hwId);
        object.addProperty("uniqueId",uniqueId);
        String[] request = new String[3];
        request[0] = base+"svc=unit/update_device_type&params={params}&sid={sid}";
        request[1] = object.toString();
        request[2] = sessionId;
        return request;
    }

    public String[] buildDetailedNotificationRequest(long unitResourceId,long[] notificationIds,String sessionId)
    {
        JsonObject object = new JsonObject();
        object.addProperty("itemId", unitResourceId);
        Gson gson = new Gson();
        String arr= gson.toJson(notificationIds);
        JsonArray array = gson.fromJson(arr,JsonArray.class);
        object.add("col", array);
        String[] request = new String[3];
        request[0] = base+"svc=resource/get_notification_data&params={params}&sid={sid}";
        request[1] = object.toString();
        request[2] = sessionId;
        return request;
    }
    public String[] buildOrderRequest(Order order,long resourceId,OrderActionType actionType,String sessionId)
    {
        JsonObject object = order.getJsonObject();
        if(actionType==OrderActionType.CREATE)
        {
            object.remove("id");
            object.addProperty("id", 0);
        }
        if(actionType==OrderActionType.CREATE||actionType==OrderActionType.UPDATE)
        {
            object.addProperty("itemId",resourceId);
        }
        object.addProperty("callMode", actionType.getValue());

        String[] request = new String[3];
        request[0] = base+"svc=order/update&params={params}&sid={sid}";
        request[1] = object.toString();
        request[2] = sessionId;
        return request;
    }





}
