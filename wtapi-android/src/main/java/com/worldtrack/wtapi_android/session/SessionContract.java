package com.worldtrack.wtapi_android.session;

import com.worldtrack.wtapi_android.callback.CRUD.CreateCallback;
import com.worldtrack.wtapi_android.callback.CRUD.DeleteCallback;
import com.worldtrack.wtapi_android.callback.CRUD.UpdateCallback;
import com.worldtrack.wtapi_android.callback.Callback;
import com.worldtrack.wtapi_android.callback.other.FileUploadCallback;
import com.worldtrack.wtapi_android.callback.other.SearchCallback;
import com.worldtrack.wtapi_android.core.WialonObject;
import com.worldtrack.wtapi_android.dataformat.HWFilter;
import com.worldtrack.wtapi_android.dataformat.OrderActionType;
import com.worldtrack.wtapi_android.dataformat.UnitDataFlag;
import com.worldtrack.wtapi_android.dataformat.UnitGroupDataFlag;
import com.worldtrack.wtapi_android.models.other.HardWareType;
import com.worldtrack.wtapi_android.models.resource.Order;
import com.worldtrack.wtapi_android.models.resource.driver.Driver;
import com.worldtrack.wtapi_android.models.resource.notification.DetailedNotification;
import com.worldtrack.wtapi_android.models.unit.CustomField;
import com.worldtrack.wtapi_android.models.unit.Sensor;
import com.worldtrack.wtapi_android.models.unit.Unit;
import com.worldtrack.wtapi_android.search.SearchSpecification;

/**
 * Worldtrack 20.08.15.
 */
public interface SessionContract {

    void searchUnits(SearchSpecification specification, UnitDataFlag[] flags, SearchCallback callback,boolean shouldLoginBefore);
    void searchUnitGroups(SearchSpecification specification, UnitGroupDataFlag[] flags, SearchCallback callback,boolean shouldLoginBefore);
    void searchDrivers(SearchSpecification specification, SearchCallback callback,boolean shouldLoginBefore);
    void searchNotifications(SearchSpecification specification, SearchCallback callback,boolean shouldLoginBefore);
    void searchHardwareTypes(HWFilter filterType,String filterValue,boolean includeType,SearchCallback callback,boolean shouldLoginBefore);
    void searchUserResourceId(SearchCallback callback,boolean shouldLoginBefore);
    void searchOrders(SearchCallback callback,String mask,boolean shouldLoginBefore);

    void createDriver(long creatorId,Driver driver,CreateCallback callback,boolean shouldLoginBefore);
    void createNotification(long creatorId,DetailedNotification notification,CreateCallback callback,boolean shouldLoginBefore);
    void createUnitGroup(long creatorId,String name,UnitGroupDataFlag[] flags,CreateCallback callback,boolean shouldLoginBefore);
    void createUnit(long creatorId,Unit unit,UnitDataFlag[] flags,CreateCallback callback,boolean shouldLoginBefore);
    void createCustomField(WialonObject object, String name, String value, CreateCallback callback,boolean shouldLoginBefore);
    void createSensor(long creatorId,Sensor sensor,CreateCallback callback,boolean shouldLoginBefore);

    void updateCustomField(WialonObject object, CustomField customField, String value, UpdateCallback callback,boolean shouldLoginBefore);
    void updateHardwareType(Unit unit,HardWareType hardWareType,UpdateCallback callback,boolean shouldLoginBefore);
    void updateNotification(long userResourceId,DetailedNotification notification,UpdateCallback callback,boolean shouldLoginBefore);

    void deleteItem(long id,DeleteCallback callback,boolean shouldLoginBefore);
    void deleteNotification(long resourceId,long notificationId,DeleteCallback callback,boolean shouldLoginBefore);
    void deleteCustomField(WialonObject object, CustomField customField, DeleteCallback callback,boolean shouldLoginBefore);

    void uploadImage(long itemId,Object fileToUpload,FileUploadCallback callback,boolean shouldLoginBefore);

    void getDetailedNotifications(long unitResourceId, long[] notificationIds, SearchCallback callback, boolean shouldLoginBefore);

    void performOrderAction(final Order order, final long resourceId, final OrderActionType actionType,final Callback callback, boolean shouldLoginBefore);









    }
