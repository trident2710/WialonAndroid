package com.worldtrack.wtapi_android.callback.CRUD;

import com.worldtrack.wtapi_android.callback.Callback;
import com.worldtrack.wtapi_android.core.WialonObject;

/**
 * Worldtrack 22.08.15.
 */
public interface CreateCallback extends Callback {
    void onObjectCreated(WialonObject object);
}
