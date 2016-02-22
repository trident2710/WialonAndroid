package com.worldtrack.wtapi_android.session;

import com.worldtrack.wtapi_android.callback.other.LoginCallback;
import com.worldtrack.wtapi_android.models.SessionData;

/**
 * Worldtrack 07.09.15.
 */
public interface CredentialHolder {

    String getToken();
    SessionData getSessionData();
    void tokenLogin(final String token,final LoginCallback callback);

    void setToken(String token);
    void setSessionData(SessionData sessionData);
}
