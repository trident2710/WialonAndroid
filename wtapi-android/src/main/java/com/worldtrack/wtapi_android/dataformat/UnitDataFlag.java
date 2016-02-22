package com.worldtrack.wtapi_android.dataformat;

/**
 * Worldtrack 19.08.15.
 */
public enum UnitDataFlag {
    baseFlag(0x00000001), //	base flag
    customProperties(0x00000002), //	custom properties
    billingProperties(0x00000004),//	billing properties
    customFields(0x00000008),//	custom fields
    image(0x00000010),//	image
    messages(0x00000020),//	messages
    GUID(0x00000040),//	GUID
    adminFields(0x00000080),//	administrative fields
    advancedProperties(0x00000100),//	advanced properties
    availableForCommands(0x00000200),//	available for current moment commands
    lastMessageAndPosition(0x00000400),//	last message and position
    sensors(0x00001000),//	sensors
    counters(0x00002000),//	counters
    maintenance(0x00008000),//	maintenance
    unitConfiguration(0x00020000),//	unit configuration in reports: trip detector and fuel consumption
    possibleCommands(0x00080000),//	list of all possible commands for current unit
    messageParameters(0x00100000),//	message parameters
    position(0x00400000),//	position
    all(0x3FFFFFFFFFFFFFFFl);//	set all possible flags to unit
    private long value;

    UnitDataFlag (long value) {
        this.value=value;
    }

    public long getValue() {
        return value;
    }
}
