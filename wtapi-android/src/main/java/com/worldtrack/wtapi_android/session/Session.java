package com.worldtrack.wtapi_android.session;

import com.worldtrack.wtapi_android.callback.CRUD.CreateCallback;
import com.worldtrack.wtapi_android.callback.CRUD.DeleteCallback;
import com.worldtrack.wtapi_android.callback.CRUD.UpdateCallback;
import com.worldtrack.wtapi_android.callback.Callback;
import com.worldtrack.wtapi_android.callback.other.FileUploadCallback;
import com.worldtrack.wtapi_android.callback.other.LoginCallback;
import com.worldtrack.wtapi_android.callback.other.RequestTaskCallback;
import com.worldtrack.wtapi_android.callback.other.SearchCallback;
import com.worldtrack.wtapi_android.core.HttpRequestTask;
import com.worldtrack.wtapi_android.core.RequestBuilder;
import com.worldtrack.wtapi_android.core.ResponseParser;
import com.worldtrack.wtapi_android.core.WialonObject;
import com.worldtrack.wtapi_android.dataformat.*;
import com.worldtrack.wtapi_android.models.SessionData;
import com.worldtrack.wtapi_android.models.other.HardWareType;
import com.worldtrack.wtapi_android.models.other.WialonError;
import com.worldtrack.wtapi_android.models.resource.Order;
import com.worldtrack.wtapi_android.models.resource.driver.Driver;
import com.worldtrack.wtapi_android.models.resource.notification.DetailedNotification;
import com.worldtrack.wtapi_android.models.unit.CustomField;
import com.worldtrack.wtapi_android.models.unit.Sensor;
import com.worldtrack.wtapi_android.models.unit.Unit;
import com.worldtrack.wtapi_android.search.SearchSpecification;

/**
 * Worldtrack 16.08.15.
 */
public class Session extends SessionPrototype implements CredentialHolder,SessionContract {

    private static Session _instance = new Session(new ResponseParser(),new RequestBuilder());

    private String token;
    private SessionData sessionData;
    ItemSession itemSession;
    ResourceSession resourceSession;
    OrderSession orderSession;

    private Session(ResponseParser parser, RequestBuilder builder) {
        super(parser, builder);
    }

    public static Session getInstance()
    {
        return _instance;
    }
    public void initSession(String token)
    {
        this.token = token;
        itemSession = new ItemSession(parser,builder,this);
        resourceSession = new ResourceSession(parser,builder,this);
        orderSession = new OrderSession(parser,builder,this);
    }

    @Override
    public void searchUnits(final SearchSpecification specification, final UnitDataFlag[] flags, final SearchCallback callback,boolean shouldLoginBefore) {
        itemSession.searchUnits(specification, flags, callback, shouldLoginBefore);
    }

    @Override
    public void searchUnitGroups(final SearchSpecification specification, final UnitGroupDataFlag[] flags, final SearchCallback callback,boolean shouldLoginBefore) {
        itemSession.searchUnitGroups(specification, flags, callback, shouldLoginBefore);
    }

    @Override
    public void searchDrivers(final SearchSpecification specification, final SearchCallback callback,boolean shouldLoginBefore) {
        resourceSession.searchDrivers(specification, callback, shouldLoginBefore);
    }

    @Override
    public void searchNotifications(final SearchSpecification specification, final SearchCallback callback,boolean shouldLoginBefore) {
        resourceSession.searchNotifications(specification, callback, shouldLoginBefore);
    }

    @Override
    public void createNotification(final long creatorId, final DetailedNotification notification, final CreateCallback callback,boolean shouldLoginBefore) {
        resourceSession.createNotification(creatorId, notification, callback, shouldLoginBefore);
    }

    @Override
    public void createDriver(final long creatorId, final Driver driver, final CreateCallback callback,boolean shouldLoginBefore) {
        resourceSession.createDriver(creatorId, driver, callback, shouldLoginBefore);
    }

    @Override
    public void createUnitGroup(final long creatorId, final String name,final UnitGroupDataFlag[] flags, final CreateCallback callback,boolean shouldLoginBefore) {
        itemSession.createUnitGroup(creatorId, name, flags, callback, shouldLoginBefore);
    }

    @Override
    public void createUnit(final long creatorId, final Unit unit,final UnitDataFlag[] flags, final CreateCallback callback,boolean shouldLoginBefore) {
        itemSession.createUnit(creatorId, unit, flags, callback, shouldLoginBefore);
    }

    @Override
    public void createSensor(final long creatorId, final Sensor sensor, final CreateCallback callback,boolean shouldLoginBefore) {
        itemSession.createSensor(creatorId, sensor, callback, shouldLoginBefore);
    }

    @Override
    public void createCustomField(final WialonObject object, final String name, final String value, final CreateCallback callback,boolean shouldLoginBefore) {
        itemSession.createCustomField(object, name, value, callback, shouldLoginBefore);
    }

    @Override
    public void searchHardwareTypes(final HWFilter filterType, final String filterValue, final boolean includeType, final SearchCallback callback,boolean shouldLoginBefore) {
        itemSession.searchHardwareTypes(filterType, filterValue, includeType, callback, shouldLoginBefore);
    }

    @Override
    public void searchUserResourceId(final SearchCallback callback,boolean shouldLoginBefore) {
        resourceSession.searchUserResourceId(callback,shouldLoginBefore);
    }

    @Override
    public void searchOrders(SearchCallback callback,String mask, boolean shouldLoginBefore) {
        orderSession.searchOrders(callback,mask,shouldLoginBefore);
    }

    @Override
    public void deleteCustomField(final WialonObject object, final CustomField customField, final DeleteCallback callback, boolean shouldLoginBefore) {
        itemSession.deleteCustomField(object,customField,callback,shouldLoginBefore);
    }


    @Override
    public void updateCustomField(final WialonObject object, final CustomField customField, final String value, final UpdateCallback callback,boolean shouldLoginBefore) {
        itemSession.updateCustomField(object, customField, value, callback, shouldLoginBefore);
    }

    @Override
    public void updateHardwareType(Unit unit, HardWareType hardWareType, UpdateCallback callback, boolean shouldLoginBefore) {
        itemSession.updateHardwareType(unit,hardWareType,callback,shouldLoginBefore);
    }

    @Override
    public void updateNotification(long userResourceId,DetailedNotification notification, UpdateCallback callback, boolean shouldLoginBefore) {
        resourceSession.updateNotification(userResourceId,notification,callback,shouldLoginBefore);
    }

    @Override
    public void deleteItem(final long id, final DeleteCallback callback, boolean shouldLoginBefore) {
        itemSession.deleteItem(id, callback, shouldLoginBefore);
    }

    @Override
    public void deleteNotification(final long resourceId, final long notificationId, final DeleteCallback callback, boolean shouldLoginBefore) {
        resourceSession.deleteNotification(resourceId, notificationId, callback, shouldLoginBefore);
    }

    @Override
    public void uploadImage(final long itemId, final Object fileToUpload, final FileUploadCallback callback, boolean shouldLoginBefore) {
        itemSession.uploadImage(itemId, fileToUpload, callback, shouldLoginBefore);
    }

    @Override
    public void getDetailedNotifications(long unitResourceId, long[] notificationIds, SearchCallback callback, boolean shouldLoginBefore) {
        resourceSession.getDetailedNotifications(unitResourceId, notificationIds, callback, shouldLoginBefore);
    }

    @Override
    public void performOrderAction(Order order, long resourceId, OrderActionType actionType, Callback callback, boolean shouldLoginBefore) {
        orderSession.performOrderAction(order,resourceId,actionType,callback,shouldLoginBefore);
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public SessionData getSessionData() {
        return sessionData;
    }

    @Override
    public void tokenLogin(final String token,final LoginCallback callback) {
        String[] loginRequest = builder.buildTokenLoginRequest(token);
        HttpRequestTask loginTask = new HttpRequestTask(loginRequest, new RequestTaskCallback() {
            @Override
            public void onResult(String response) {
                WialonObject object = parser.parseSessionData(parser.parseJsonObjectFromString(response));
                if (!isWialonError(object)) {
                    sessionData = (SessionData) parser.parseSessionData(parser.parseJsonObjectFromString(response));
                    callback.onLoginSuccessful(sessionData);
                } else callback.onError(((WialonError) object).getError());
            }
        });
        loginTask.execute();
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public void setSessionData(SessionData sessionData) {
        this.sessionData = sessionData;
    }
}
