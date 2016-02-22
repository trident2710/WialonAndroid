package com.worldtrack.wtapi_android.dataformat;

/**
 * Worldtrack 19.08.15.
 */
public enum ResourceDataFlag {

    baseFlag(0x00000001), //	base flag
    /** Drivers plugin */
    drivers(0x00000100),
    /** Jobs plugin */
    jobs(0x00000200),
    /** Notifications plugin */
    notifications(0x00000400),
    /** POI plugin */
    poi(0x00000800),
    /** Geofences plugin */
    zones(0x00001000),
    /** Reports plugin */
    reports(0x00002000),
    /** Agro plugins */
    agro(0x01000000),
    /** Driver units */
    driverUnits(0x00004000),
    /** Driver groups plugin */
    driverGroups(0x00008000),
    /** Trailers plugin */
    trailers(0x00010000),
    /** Trailer groups plugin */
    trailerGroups(0x00020000),
    /** Trailer units */
    trailerUnits(0x00040000),

    orders(0x00080000);
    /** Flag value */
    private long value;

    ResourceDataFlag (long value) {
        this.value=value;
    }

    public long getValue() {
        return value;
    }
}
