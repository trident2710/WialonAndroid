package com.worldtrack.wtapi_android.callback.other;

import com.worldtrack.wtapi_android.callback.Callback;
import com.worldtrack.wtapi_android.core.WialonObject;

/**
 * Worldtrack 16.08.15.
 */
public interface SearchCallback extends Callback {
    void onSearchSuccessful(WialonObject[] result);
}
