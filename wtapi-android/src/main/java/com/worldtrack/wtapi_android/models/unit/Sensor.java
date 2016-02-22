package com.worldtrack.wtapi_android.models.unit;

import com.google.gson.JsonObject;
import com.worldtrack.wtapi_android.core.WialonObject;

/**
 * Worldtrack 18.08.15.
 */
public class Sensor extends WialonObject{
    public Sensor(JsonObject object) {
        super(object);
    }

    public Sensor(Sensor wialonObject) {
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
//
//{
//    "sens":{		/* sensors */
//    <text>:{		/* sequence number of sensor */
//        "id":<long>,		/* ID */
//        "n":<text>,		/* name */
//                "t":<text>,		/* type */
//                "d":<text>,		/* description */
//                "m":<text>,		/* metrics */
//                "p":<text>,		/* parameter */
//                "f":<uint>,		/* sensor flags */
//                "c":<text>,		/* configuration */
//                "vt":<uint>,		/* validation type */
//                "vs":<uint>,		/* validation sensor ID */
//                "tbl":[			/* calculation table */
//        {			/* parameters */
//            "x":<double>,
//            "a":<double>,
//            "b":<double>
//        }
//        ]
//    },
//    ...
//},
//    "sens_max":<long>	/*  maximal count of sensors (-1 - unlimited) */
//}
}
