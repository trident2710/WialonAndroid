package com.worldtrack.wtapi_android.callback.other;

import com.worldtrack.wtapi_android.callback.Callback;
import com.worldtrack.wtapi_android.models.SessionData;

/**
 * Worldtrack 16.08.15.
 */
public interface LoginCallback extends Callback {
    void onLoginSuccessful(SessionData sessionData);
}
