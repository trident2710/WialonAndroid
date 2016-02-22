package com.worldtrack.wtapi_android.models.other;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.worldtrack.wtapi_android.core.WialonObject;

/**
 * Worldtrack 20.08.15.
 */
public class Token extends WialonObject{

    public Token(JsonObject object){
        super(object);
    }
    public Token(Token wialonObject){
        super(wialonObject);
    }

    String getTokenName()
    {
        return jsonObject.get("h").getAsString();
    }
    int getAccessFlags()
    {
        return jsonObject.get("fl").getAsInt();
    }
    long[] getItemIds()
    {
        JsonArray array = jsonObject.get("items").getAsJsonArray();
        long[] ids = new long[array.size()];
        for (int i = 0;i<array.size();i++)
        {
            ids[i] = array.get(i).getAsLong();
        }
        return ids;
    }

//    {
//        "h":<text>,		/* unique token name, 72 symbols */
//            "app":<text>,		/* application name */
//            "at":<uint>,		/* token activation time, UNIX-time */
//            "ct":<uint>,		/* token creation time, UNIX-time */
//            "dur":<uint>,		/* token duration after activation, секунды */
//            "fl":<uint>,		/* access flags */
//            "items":[<long>],	/* list of item ids with token granted access */
//        "p":<text>		/* custom parameters, value must be object or an array of objects */
//    }
}
