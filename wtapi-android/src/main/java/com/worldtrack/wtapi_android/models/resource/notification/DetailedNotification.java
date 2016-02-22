package com.worldtrack.wtapi_android.models.resource.notification;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.worldtrack.wtapi_android.core.WialonObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Worldtrack 17.08.15.
 */
public class DetailedNotification extends Notification {

    public DetailedNotification(JsonObject object) {
        super(object);
    }

    public DetailedNotification(DetailedNotification wialonObject) {
        super(wialonObject);
    }


    public long getId() {
        return jsonObject.get("id").getAsLong();
    }

    public void setId(long id) {
        jsonObject.remove("id");
        jsonObject.addProperty("id", id);
    }

    public String getName() {
        return jsonObject.get("n").getAsString();
    }

    public void setName(String name) {
        jsonObject.remove("n");
        jsonObject.addProperty("n", name);
    }

    public String getNotificationText() {
        return jsonObject.get("txt").getAsString();
    }

    public void setNotificationText(String text) {
        jsonObject.remove("txt");
        jsonObject.addProperty("txt", text);
    }



//    {
//        "id":<long>,	/* notification ID */
//        "n":<text>,	/* name */
//            "txt":<text>,	/* text of notification */
//            "ta":<uint>,	/* activation time (UNIX format) */
//            "td":<uint>,	/* deactivation time (UNIX format) */
//            "ma":<uint>,	/* maximal alarms count (0 - unlimited) */
//            "mmtd":<uint>,	/* maximal time interval between messages (seconds) */
//            "cdt":<uint>,	/* timeout of alarm (seconds) */
//            "mast":<uint>,	/* minimal duration of alert state (seconds) */
//            "mpst":<uint>,	/* minimal duration of previous state (seconds) */
//            "cp":<uint>,	/* period of control relative to current time (seconds) */
//            "fl":<uint>,	/* notification flags (see below) */
//            "tz":<uint>,	/* timezone */
//            "la":<text>,	/* user language (two-lettered code) */
//            "ac":<uint>,	/* alarms count */
//            "sch":{		/* time limitation */
//        "f1":<uint>,	/* beginning of interval 1 (minutes from midnight) */
//                "f2":<uint>,	/* beginning of interval 2 (minutes from midnight) */
//                "t1":<uint>,	/* ending of interval 1 (minutes from midnight) */
//                "t2":<uint>,	/* ending of interval 2 (minutes from midnight) */
//                "m":<uint>,	/* days of month mask [1: 2^0, 31: 2^30] */
//                "y":<uint>,	/* months mask [Jan: 2^0, Dec: 2^11] */
//                "w":<uint>	/* days of week mask [Mon: 2^0, Sun: 2^6] */
//    },
//        "un":[<long>],	/* array units IDS */
//        "act":[			/* actions */
//        {
//            "t":<text>,		/* action type (see below) */
//                "p":{			/* parameters */
//            <text>:<text>,		/* parameter name: value */
//            ...
//        },
//            ...
//        }
//        ],
//        "trg":{			/* control */
//        "t":<text>,		/* control type (see below) */
//                "p":{			/* parameters */
//            <text>:<text>,		/* parameter name: value */
//            ...
//        }
//    },
//        "ct":<uint>,        /* creation time */
//            "mt":<uint>         /* last modification time */
//    }

}
