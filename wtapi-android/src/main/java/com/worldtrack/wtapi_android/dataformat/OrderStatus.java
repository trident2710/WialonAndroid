package com.worldtrack.wtapi_android.dataformat;

/**
 * Worldtrack 17.09.15.
 */
public enum OrderStatus {
    INACTIVE(0,"inactive (no unit assigned)"),
    ACTIVE(1,"active (unit assigned)"),
    COMPLETED_IN_TIME(2,"completed in time"),
    COMPLETED_OVERDUE(3,"completed overdue"),
    CANCELED(4,"cancelled (not used)");

    String message;
    int type;

    public String getMessage() {
        return message;
    }

    public int getType() {
        return type;
    }

    OrderStatus(int type,String message) {
        this.message = message;
        this.type = type;
    }
}
