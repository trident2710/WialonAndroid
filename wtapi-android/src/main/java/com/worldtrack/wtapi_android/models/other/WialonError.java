package com.worldtrack.wtapi_android.models.other;

import com.google.gson.JsonObject;
import com.worldtrack.wtapi_android.core.WialonObject;
import com.worldtrack.wtapi_android.dataformat.*;
import com.worldtrack.wtapi_android.dataformat.Error;

/**
 * Worldtrack 07.09.15.
 */
public class WialonError extends WialonObject {
    Error error;
    public WialonError(JsonObject object) {
        super(object);
        checkError();

    }
    void checkError()
    {
        if(jsonObject.has("error"))
        {
            for (Error error: Error.values())
            {
                if(error.getErrorCode()==jsonObject.get("error").getAsInt())
                {
                    this.error = error;
                    return;
                }
            }
        }
        error = Error.UNKNOWN_ERROR;
    }

    public Error getError() {
        return error;
    }

}
