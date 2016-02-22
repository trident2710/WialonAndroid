package com.worldtrack.wtapi_android.dataformat;

/**
 * Worldtrack 19.08.15.
 */
public enum UnitGroupDataFlag {
    baseFlag(0x00000001),//	base flag
    customProperties(0x00000002),//	custom properties
    billingProperties(0x00000004),//	billing properties
    customFields(0x00000008),//	custom fields
    image(0x00000010),//	image
    GUID(0x00000040),//	GUID
    administrativeFields(0x00000080),//	administrative fields
    all(0x3FFFFFFFFFFFFFFFl);//	set all possible flags to unit group

    private long value;

    UnitGroupDataFlag (long value) {
        this.value=value;
    }

    public long getValue() {
        return value;
    }
}
