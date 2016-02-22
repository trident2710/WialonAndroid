package com.worldtrack.wtapi_android.dataformat;

/**
 * Worldtrack 20.08.15.
 */
public enum TokenFlag {
    ONLINE_TRACKING(0x100), //online tracking
    VIEW_ACCESS(0x200),  //view access to most data
    MODIFICATION_NON_SENSITIVE(0x400),  //modification of non-sensitive data
    MODIFICATION_SENSITIVE(0x800),  //modification of sensitive data
    MODIFICATION_CRITICAL(0x1000), //modification of critical data, including messages deletion
    COMMUNICATION(0x2000), //communication
    FULL_ACCESS(-1); //unlimited operation as authorizated user (allows to manage user tokens)

    private long value;

    TokenFlag (long value) {
        this.value=value;
    }

    public long getValue() {
        return value;
    }
}
