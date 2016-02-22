package com.worldtrack.wtapi_android.dataformat;

/**
 * Worldtrack 22.08.15.
 */
public enum ActionType {
    CREATE("create"),
    UPDATE("update"),
    DELETE("delete");

    String value;

    public String getValue()
    {
        return value;
    }

    ActionType(String value)
    {
        this.value = value;
    }
}
