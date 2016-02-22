package com.worldtrack.wtapi_android.search;

import com.google.gson.JsonObject;
import com.worldtrack.wtapi_android.core.WialonObject;

/**
 * Worldtrack 19.08.15.
 */
public class SearchSpecification extends WialonObject{

    public SearchSpecification(JsonObject object) {
        super(object);
    }

    public SearchSpecification(SearchSpecification wialonObject) {
        super(wialonObject);
    }

    public SearchSpecification(ElementType elementType, ItemProperty itemProperty, ItemProperty sortType, String valueMask)
    {
        this.jsonObject = new JsonObject();
        fillJsonObject(elementType,itemProperty,sortType,valueMask);
    }

    private void fillJsonObject(ElementType elementType, ItemProperty itemProperty, ItemProperty sortType, String valueMask)
    {
        //"spec":{     "itemsType":"avl_unit",     "propName":"sys_name",     "propValueMask":"*",     "sortType":"sys_name"}
        jsonObject.addProperty("itemsType",elementType.getValue());
        jsonObject.addProperty("propName",itemProperty.getValue());
        jsonObject.addProperty("propValueMask",valueMask);
        jsonObject.addProperty("sortType",sortType.getValue());
    }

    public String getElementType() {
        return jsonObject.get("itemsType").getAsString();
    }

    public String getItemProperty() {
        return jsonObject.get("propName").getAsString();
    }

    public String getSortType() {
        return jsonObject.get("sortType").getAsString();
    }

    public String getValueMask() {
        return jsonObject.get("propValueMask").getAsString();
    }

    public void setElementType(ElementType elementType) {
        jsonObject.remove("itemsType");
        jsonObject.addProperty("itemsType",elementType.getValue());
    }

    public void setItemProperty(ItemProperty itemProperty) {
        jsonObject.remove("propName");
        jsonObject.addProperty("propName", itemProperty.getValue());
    }

    public void setSortType(ItemProperty sortType) {
        jsonObject.remove("sortType");
        jsonObject.addProperty("sortType", sortType.getValue());
    }

    public void setValueMask(String valueMask) {
        jsonObject.remove("propValueMask");
        jsonObject.addProperty("propValueMask", valueMask);

    }

    /** Item types constants */
    public enum ElementType{
        /** hardware type */
        hardwareType("avl_hw") ,
        /** resource */
        resource("avl_resource"),
        /**retranslator **/
        retranslator("avl_retranslator"),
        /** unit **/
        unit("avl_unit"),
        /** unit group **/
        unitGroup("avl_unit_group"),
        /** user **/
        user("user"),
        /** route **/
        route("avl_route");

        private String value;

        ElementType (String value) {
            this.value=value;
        }

        public String getValue() {
            return value;
        }
    }

    /** List of item properties **/
    public enum ItemProperty{
        name("sys_name"), //– item name;
        id("sys_id"), //– item ID;
        hardwareId("sys_unique_id"), //– hardware type ID;
        number("sys_phone_number"), //– unit phone number;
        secondNumber("sys_phone_number2"), //– unit second phone number;
        creatorId("sys_user_creator"), //– creator ID;
        creatorName("rel_user_creator_name"), //– creator name;
        accountId("sys_billing_account_guid"), //– account ID;
        accountName("rel_billing_account_name"), //– account name;
        billingPlan("rel_billing_plan_name"), //– billing plan name;
        hardwareState("sys_comm_state"), //– hardware state (1 - enabled, 0 - disabled);
        hardwareType("sys_hw_type"), //– hardware type;
        hardwareName("rel_hw_type_name"), //– hardware name;
        accountBalance("sys_account_balance"), //– account balance;
        accountDays("sys_account_days"), //– account days;
        dealerRights("sys_account_enable_parent"), //– dealer rights (1 - on, 0 - off);
        accountBlocked("sys_account_disabled"), //– account blocked (1 - yes, 0 - no);
        lastModification("rel_account_disabled_mod_time"), //– last modification time for sys_account_disabled, UNIX-time;
        numberOfUnits("rel_account_units_usage"), //– number of units used in account;
        lastMessageTime("rel_last_msg_date"), //– last message time, UNIX-time;
        resourceIsAccount("rel_is_account"), //– whether resource is account (1 - yes, 0 - no);
        lastLogin("login_date"), //– last login time, UNIX-time;
        retranslatorEnabled("retranslator_enabled"), //– whether retranslator enabled ( 1 - yes, 0 - no).

        // additional properties for Unit Items
        unitSensors("unit_sensors"),
        unitCommands("unit_commands"),
        serviceIntervals("service_intervals"),

        // additional properties for Resource Items
        drivers("drivers"),
        driverGroups("driver_groups"),
        jobs("jobs"),
        notifications("notifications"),
        pois("pois"),
        trailers("trailers"),
        trailer_groups("trailer_groups"),
        zones_library("zones_library"),
        reporttemplates("reporttemplates"),
        // additional properties for Route Items
        rounds("rounds"),
        route_schedules("route_schedules"),
        retranslator_units("retranslator_units"),
        // other additional properties
        custom_fields("custom_fields"),
        admin_fields("admin_fields");

        private String value;

        ItemProperty (String value) {
            this.value=value;
        }

        public String getValue() {
            return value;
        }
    }

}
