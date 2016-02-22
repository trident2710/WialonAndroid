package com.worldtrack.wtapi_android.session;

import com.worldtrack.wtapi_android.core.RequestBuilder;
import com.worldtrack.wtapi_android.core.ResponseParser;
import com.worldtrack.wtapi_android.core.WialonObject;
import com.worldtrack.wtapi_android.models.other.WialonError;

/**
 * Worldtrack 07.09.15.
 */
public abstract class SessionPrototype{
    RequestBuilder builder;
    ResponseParser parser;
    CredentialHolder credentialHolder;


    public SessionPrototype(ResponseParser parser, RequestBuilder builder,CredentialHolder holder) {
        this.parser = parser;
        this.builder = builder;
        this.credentialHolder  = holder;

    }
    public SessionPrototype(ResponseParser parser, RequestBuilder builder) {
        this.parser = parser;
        this.builder = builder;
        this.credentialHolder  = null;

    }

    protected boolean isWialonError(WialonObject object)
    {
        try {
            WialonError error = (WialonError)object;
            return true;
        } catch (Exception e)
        {
            return false;
        }
    }


}
