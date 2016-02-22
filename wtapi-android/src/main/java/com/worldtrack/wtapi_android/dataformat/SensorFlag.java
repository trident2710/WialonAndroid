package com.worldtrack.wtapi_android.dataformat;

/**
 * Worldtrack 22.08.15.
 */
public enum SensorFlag {
    ABSOLUTE_FUEL_CONSUMPTION("absolute fuel consumption"),// - ABSOLUTE FUEL CONSUMPTION SENSOR;
    ACCELEROMETER("accelerometer"), //- ACCELEROMETER;
    ALARM_TRIGGER("alarm trigger"), //- ALARM TRIGGER;
    COUNTER("counter"), //- COUNTER SENSOR;
    CUSTOM("custom"), //- CUSTOM SENSOR;
    DIGITAL("digital"), //- CUSTOM DIGITAL SENSOR;
    DRIVER("driver"),//- DRIVER BINDING;
    ENGINE_EFFICIENCY("engine efficiency"), //- ENGINE EFFICIENCY SENSOR;
    ENGINE_HOURS("engine hours"), //- ABSOLUTE ENGINE HOURS;
    ENGINE_OPERATION("engine operation"), //- ENGINE IGNITION SENSOR;
    ENGINE_RPM("engine rpm"), //- ENGINE REVS SENSOR;
    FUEL_LEVEL_IMPULSE_SENSOR("fuel level impulse sensor"), //- FUEL LEVEL IMPULSE SENSOR;
    FUEL_LEVEL("fuel level"), //- FUEL LEVEL SENSOR;
    IMPULSE_FUEL_CONSUMPTION("impulse fuel consumption"), //- IMPULSE FUEL CONSUMPTION SENSOR;
    INSTANT_FUEL_CONSUMPTION("instant fuel consumption"), //- INSTANT FUEL CONSUMPTION SENSOR;
    MILEAGE("mileage"), //- MILEAGE SENSOR;
    ODOMETER("odometer"), //- RELATIVE ODOMETER;
    PRIVATE_MODE("private mode"), //- PRIVATE MODE;
    RELATIVE_ENGINE_HOURS("relative engine hours"), //- RELATIVE ENGINE HOURS;
    TEMPERATURE_COEFFICIENT("temperature coefficient"), //- TEMPERATURE COEFFICIENT;
    TEMPERATURE("temperature"), //- TEMPERATURE SENSOR;
    TRAILER("trailer"), //- TRAILER BINDING;
    VOLTAGE("voltage"), //- VOLTAGE SENSOR;
    WEIGHT_SENSOR("weight sensor"); //- WEIGHT SENSOR.

    String value;

    public String getValue()
    {
        return value;
    }
    SensorFlag(String value)
    {
        this.value = value;
    }
    }
