package com.worldtrack.wtapi_android.models.resource.driver;

import com.google.gson.JsonObject;
import com.worldtrack.wtapi_android.core.WialonObject;

/**
 * Worldtrack 19.08.15.
 */
public class Driver extends WialonObject {

    public Driver(JsonObject object) {
        super(object);
    }

    public Driver(Driver wialonObject) {
        super(wialonObject);
    }

    public long getId() {
        return jsonObject.get("id").getAsLong();
    }

    public void setId(long id){
        jsonObject.remove("id");
        jsonObject.addProperty("id",id);
    }

    public String getName() {
        return jsonObject.get("n").getAsString();
    }

    public void setName(String name){
        jsonObject.remove("n");
        jsonObject.addProperty("n", name);
    }

    public long getBindedUnit()
    {
        return jsonObject.get("bu").getAsLong();
    }

    public String getCode(){return jsonObject.get("c").getAsString();}

    public JsonObject getCustomFields(){return jsonObject.get("jp").getAsJsonObject();}

    public String getDescription(){return jsonObject.get("ds").getAsString();}

    public String getPhoneNumber(){return jsonObject.get("p").getAsString();}

    public double getImageAspectRatio(){return jsonObject.get("r").getAsDouble();}

    public long getPreviousBindedUnit()
    {
        return jsonObject.get("pu").getAsLong();
    }

    public long getLastBindingTime()
    {
        return jsonObject.get("bt").getAsLong();
    }

    public JsonObject getPosition()
    {
        return jsonObject.get("pos").getAsJsonObject();
    }

//    {
//        "drvrs":{
//        <text>:{	/* sequence number of driver */
//            "id":<long>,	/* ID */
//            "n":<text>,	/* name */
//            "c":<text>,	/* code */
//            "jp": {			/* custom fields */
//                <text>:<text>,		/* name: value */
//                ...
//            },
//            "ds":<text>,	/* description */
//            "p":<text>,	/* phone number */
//            "r":<double>,	/* image aspect ratio */
//            "ck":<ushort>	/* check sum (CRC16) */
//            "bu":<long>,	/* binded unit */
//            "pu":<long>,	/* previous binded unit */
//            "bt":<uint>,	/* time of last binging/unbinding */
//            "pos":{		/* position */
//                "y":<double>,	/* latitude */
//                "x":<double>	/* longitude */
//            },
//            infr: { /*parameters for analysis infringements of work and rest of drivers AETR */
//                "a": <uint>,	/* current activity */
//                        "t": <uint>,	/* the start time of this activity */
//                        "ud": <uint>,	/* duration of uninterrupted driving before the current activity */
//                        "ur": <uint>,	/* duration of uninterrupted rest before the current activity */
//                        "uil": <uint>,	/* allowed duration of uninterrupted driving */
//                        "uim": <uint>,	/* duration of uninterrupted driving to which the violation is infringement minor */
//                        "uis": <uint>,	/* duration of uninterrupted driving to which the violation is infringement serious (above - very serious) */
//                        "uir": <uint>,	/* duration of required rest */
//                        "ddt": <uint>,	/* daily driving time before the current activity */
//                        "ddc": <uint>,	/* allowed number of prolonged daily driving in this week */
//                        "ddil": <uint>,	/* allowed duration of daily driving */
//                        "ddim": <uint>,	/* allowed duration of daily driving (minor infringement ) */
//                        "ddis": <uint>,	/* allowed duration of daily driving (serious infringement(above very serious)) */
//                        "wdt": <uint>,	/* duration of weekly driving before the current activity */
//                        "wdil": <uint>,	/* allowed duration of weekly driving */
//                        "wdim": <uint>,	/* allowed duration of weekly driving (minor infringement) */
//                        "wdis": <uint>,	/* allowed duration of weekly driving (serious infringement(above very serious)) */
//                        "twdt": <uint>,	/* duration for two weeks of driving before the current activity */
//                        "twdil": <uint>,/* allowed duration for two weeks of driving */
//                        "twdim": <uint>,/* allowed duration for two weeks of driving (minor infringement) */
//                        "twdis": <uint>,/* allowed duration for two weeks of driving (serious infringement(above very serious)) */
//                        "drt": <uint>,	/* duration of the previous daily rest */
//                        "drd": <uint>,	/* duration of the required daily rest */
//                        "dril": <uint>,	/* time before which daily rest should occure */
//                        "drim": <uint>,	/* time before which an infringement is considered to be minor */
//                        "dris": <uint>,	/* time before which an infringement is considered to be serious(above very serious) */
//                        "wrt": <uint>,	/* duration of the previous weekly rest */
//                        "wrd": <uint>,	/* duration of the required weekly rest */
//                        "wril": <uint>,	/* time before which weekly rest should occure */
//                        "wrim": <uint>,	/* time before which an infringement is considered to be minor */
//                        "wris": <uint>	/* time before which an infringement is considered to be serious(above very serious) */
//            }
//        },
//        ...
//    },
//        "drvrsmax":<long>	/* maximal count of drivers (-1 - unlimited) */
//    }
}
