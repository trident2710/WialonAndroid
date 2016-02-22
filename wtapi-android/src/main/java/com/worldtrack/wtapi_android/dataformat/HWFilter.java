package com.worldtrack.wtapi_android.dataformat;

/**
 * Worldtrack 22.08.15.
 */
public enum HWFilter {
    HARDWARE_NAME("-name"),
    HARDWARE_ID("-id"),
    HARDWARE_TYPE("-type"),

    HARDWARE_TYPE_AUTO("auto"),
    HARDWARE_TYPE_TRACKER("tracker"),
    HARDWARE_TYPE_MOBILE("mobile"),
    HARDWARE_TYPE_SOFT("soft");

    String value;
    public String getValue()
    {
        return value;
    }

    HWFilter(String value)
    {
        this.value = value;
    }

}
