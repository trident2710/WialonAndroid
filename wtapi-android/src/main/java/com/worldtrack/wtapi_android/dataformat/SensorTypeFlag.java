package com.worldtrack.wtapi_android.dataformat;

/**
 * Worldtrack 22.08.15.
 */
public enum SensorTypeFlag {

    TYPE_INSTANT(0x01),	//sensor type: instant
    TYPE_DIFFERENTIAL(0x02),	//sensor type: differential
    TYPE_DIFFERENTIAL_OVERFLOW(0x03),	//sensor type: differential with overflow (2 bytes)
    TYPE_SWITCH_OFF_ON(0x04),	//sensor type: switch from off to on
    TYPE_SWITCH_ON_OFF(0x05),	//sensor type: switch from on to off
    TYPE_WITH_OVERFLOW(0x20),	//activate “With overflow” option (see below)
    TYPE_APPLY_BOUNDS(0x40);	//apply lower/upper bounds AFTER calculation (see below)

    int value;

    public int getValue()
    {
        return value;
    }
    SensorTypeFlag(int value)
    {
        this.value = value;
    }
}
