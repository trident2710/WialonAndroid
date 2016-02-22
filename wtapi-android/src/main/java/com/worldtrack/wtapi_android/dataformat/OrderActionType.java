package com.worldtrack.wtapi_android.dataformat;

/**
 * Worldtrack 18.09.15.
 */
public enum OrderActionType {
    CREATE("create"),
    UPDATE("update"),
    DELETE("delete"),
    ASSIGN("assign"),
    REGISTER("register");

    String value;

    public String getValue()
    {
        return value;
    }

    OrderActionType(String value)
    {
        this.value = value;
    }
}
