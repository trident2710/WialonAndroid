package com.worldtrack.wtapi_android.session;

import com.worldtrack.wtapi_android.callback.CRUD.CreateCallback;
import com.worldtrack.wtapi_android.callback.CRUD.DeleteCallback;
import com.worldtrack.wtapi_android.callback.CRUD.UpdateCallback;
import com.worldtrack.wtapi_android.callback.other.LoginCallback;
import com.worldtrack.wtapi_android.callback.other.RequestTaskCallback;
import com.worldtrack.wtapi_android.callback.other.SearchCallback;
import com.worldtrack.wtapi_android.core.HttpRequestTask;
import com.worldtrack.wtapi_android.core.RequestBuilder;
import com.worldtrack.wtapi_android.core.ResponseParser;
import com.worldtrack.wtapi_android.core.WialonObject;
import com.worldtrack.wtapi_android.dataformat.*;
import com.worldtrack.wtapi_android.models.SessionData;
import com.worldtrack.wtapi_android.models.other.WialonError;
import com.worldtrack.wtapi_android.models.resource.driver.Driver;
import com.worldtrack.wtapi_android.models.resource.notification.DetailedNotification;
import com.worldtrack.wtapi_android.search.SearchSpecification;

/**
 * Worldtrack 07.09.15.
 */
final class ResourceSession extends SessionPrototype {

    public ResourceSession(ResponseParser parser, RequestBuilder builder, CredentialHolder credentialHolder) {
        super(parser, builder, credentialHolder);
    }

    public void searchDrivers(final SearchSpecification specification, final SearchCallback callback, boolean shouldLoginBefore) {

        if (shouldLoginBefore)
            credentialHolder.tokenLogin(credentialHolder.getToken(), new LoginCallback() {
                @Override
                public void onLoginSuccessful(SessionData sessionData) {
                    searchDriversRequest(specification, callback);
                }

                @Override
                public void onError(com.worldtrack.wtapi_android.dataformat.Error error) {
                    callback.onError(error);
                }
            });
        else
            searchDriversRequest(specification, callback);
    }

    private void searchDriversRequest(final SearchSpecification specification, final SearchCallback callback) {
        String[] request = builder.buildSearchRequest(specification, ResourceDataFlag.drivers.getValue(), credentialHolder.getSessionData().getSessionID());
        HttpRequestTask task = new HttpRequestTask(request, new RequestTaskCallback() {
            @Override
            public void onResult(String response) {
                WialonObject[] objects = parser.parseSearchAsDrivers(parser.parseJsonObjectFromString(response));
                if(objects.length>0)
                {
                    if (!isWialonError(objects[0])) {
                        callback.onSearchSuccessful(objects);
                    } else callback.onError(((WialonError) objects[0]).getError());
                } else callback.onError(com.worldtrack.wtapi_android.dataformat.Error.NO_ITEMS_FOUND);
            }
        });
        task.execute();
    }

    public void searchUserResourceId(final SearchCallback callback,boolean shouldLoginBefore) {
        if (shouldLoginBefore)
            credentialHolder.tokenLogin(credentialHolder.getToken(), new LoginCallback() {
                @Override
                public void onLoginSuccessful(SessionData sessionData) {
                    searchUserResourceIdRequest(callback);
                }

                @Override
                public void onError(com.worldtrack.wtapi_android.dataformat.Error error) {
                    callback.onError(error);
                }
            });
        else
            searchUserResourceIdRequest(callback);
    }

    private void searchUserResourceIdRequest(final SearchCallback callback) {
        SearchSpecification specification = new SearchSpecification(SearchSpecification.ElementType.resource, SearchSpecification.ItemProperty.name, SearchSpecification.ItemProperty.name,"*");
        String[] request = builder.buildSearchRequest(specification, ResourceDataFlag.baseFlag.getValue(), credentialHolder.getSessionData().getSessionID());
        HttpRequestTask task = new HttpRequestTask(request, new RequestTaskCallback() {
            @Override
            public void onResult(String response) {
                WialonObject object = parser.parseUserResourceId(parser.parseJsonObjectFromString(response));
                if (!isWialonError(object)) {
                    callback.onSearchSuccessful(new WialonObject[]{object});
                } else callback.onError(((WialonError) object).getError());

            }
        });
        task.execute();
    }

    public void searchNotifications(final SearchSpecification specification, final SearchCallback callback, boolean shouldLoginBefore) {

        if (shouldLoginBefore)
            credentialHolder.tokenLogin(credentialHolder.getToken(), new LoginCallback() {
                @Override
                public void onLoginSuccessful(SessionData sessionData) {
                    searchNotificationsRequest(specification, callback);
                }

                @Override
                public void onError(com.worldtrack.wtapi_android.dataformat.Error error) {
                    callback.onError(error);
                }
            });
        else
            searchNotificationsRequest(specification, callback);
    }

    private void searchNotificationsRequest(final SearchSpecification specification, final SearchCallback callback) {
        String[] request = builder.buildSearchRequest(specification, ResourceDataFlag.notifications.getValue(), credentialHolder.getSessionData().getSessionID());
        HttpRequestTask task = new HttpRequestTask(request, new RequestTaskCallback() {
            @Override
            public void onResult(String response) {
                WialonObject[] objects = parser.parseSearchAsNotifications(parser.parseJsonObjectFromString(response));
                if(objects.length>0)
                {
                    if (!isWialonError(objects[0])) {
                        callback.onSearchSuccessful(objects);
                    } else callback.onError(((WialonError) objects[0]).getError());
                } else callback.onError(com.worldtrack.wtapi_android.dataformat.Error.NO_ITEMS_FOUND);

            }
        });
        task.execute();
    }

    public void createDriver(final long creatorId, final Driver driver, final CreateCallback callback, boolean shouldLoginBefore) {
        if (shouldLoginBefore)
            credentialHolder.tokenLogin(credentialHolder.getToken(), new LoginCallback() {
                @Override
                public void onLoginSuccessful(SessionData sessionData) {
                    createDriverRequest(creatorId, driver, callback);
                }

                @Override
                public void onError(com.worldtrack.wtapi_android.dataformat.Error error) {
                    callback.onError(error);
                }
            });
        else
            createDriverRequest(creatorId, driver, callback);
    }

    private void createDriverRequest(final long creatorId, final Driver driver, final CreateCallback callback) {
        String[] request = builder.buildDriverCRUDRequest(credentialHolder.getSessionData().getSessionID(), ActionType.CREATE, creatorId, driver);
        HttpRequestTask task = new HttpRequestTask(request, new RequestTaskCallback() {
            @Override
            public void onResult(String response) {
                WialonObject object = parser.parseCreateUpdateResponseDriver(parser.parseJsonObjectFromString(response));
                if (!isWialonError(object)) {
                    callback.onObjectCreated(object);
                } else callback.onError(((WialonError) object).getError());
            }
        });
        task.execute();
    }

    public void createNotification(final long creatorId, final DetailedNotification notification, final CreateCallback callback, boolean shouldLoginBefore) {
        if (shouldLoginBefore)
            credentialHolder.tokenLogin(credentialHolder.getToken(), new LoginCallback() {
                @Override
                public void onLoginSuccessful(SessionData sessionData) {
                    createNotificationRequest(creatorId, notification, callback);
                }

                @Override
                public void onError(com.worldtrack.wtapi_android.dataformat.Error error) {
                    callback.onError(error);
                }
            });
        else
            createNotificationRequest(creatorId, notification, callback);

    }

    private void createNotificationRequest(final long creatorId, final DetailedNotification notification, final CreateCallback callback) {
        String[] request = builder.buildNotificationCRUDRequest(credentialHolder.getSessionData().getSessionID(), ActionType.CREATE, creatorId, notification);
        HttpRequestTask task = new HttpRequestTask(request, new RequestTaskCallback() {
            @Override
            public void onResult(String response) {
                WialonObject object = parser.parseCreateUpdateResponseNotification(parser.parseJsonObjectFromString(response));
                if (!isWialonError(object)) {
                    callback.onObjectCreated(object);
                } else callback.onError(((WialonError) object).getError());
            }
        });
        task.execute();
    }

    public void deleteNotification(final long resourceId, final long notificationId, final DeleteCallback callback, boolean shouldLoginBefore) {
        if (shouldLoginBefore)
            credentialHolder.tokenLogin(credentialHolder.getToken(), new LoginCallback() {
                @Override
                public void onLoginSuccessful(SessionData sessionData) {
                    deleteNotificationRequest(resourceId,notificationId,callback);
                }

                @Override
                public void onError(com.worldtrack.wtapi_android.dataformat.Error error) {
                    callback.onError(error);
                }
            });
        else
            deleteNotificationRequest(resourceId, notificationId, callback);
    }

    private void deleteNotificationRequest(final long resourceId, final long notificationId, final DeleteCallback callback) {
        String[] request = builder.buildDeleteNotificationRequest(resourceId, notificationId, credentialHolder.getSessionData().getSessionID());
        HttpRequestTask task = new HttpRequestTask(request, new RequestTaskCallback() {
            @Override
            public void onResult(String response) {
                WialonObject object = parser.parseDeleteResponse(parser.parseJsonObjectFromString(response));
                if (!isWialonError(object)) {
                    callback.onObjectDeleted(notificationId);
                } else callback.onError(((WialonError) object).getError());
            }
        });
        task.execute();
    }

    public void getDetailedNotifications(final long unitResourceId,final long[] notificationIds,final SearchCallback callback,boolean shouldLoginBefore)
    {
        if (shouldLoginBefore)
            credentialHolder.tokenLogin(credentialHolder.getToken(), new LoginCallback() {
                @Override
                public void onLoginSuccessful(SessionData sessionData) {
                    getDetailedNotificationsRequest(unitResourceId, notificationIds, callback);
                }

                @Override
                public void onError(com.worldtrack.wtapi_android.dataformat.Error error) {
                    callback.onError(error);
                }
            });
        else
            getDetailedNotificationsRequest(unitResourceId, notificationIds, callback);
    }

    private void getDetailedNotificationsRequest(long unitResourceId,long[] notificationIds,final SearchCallback callback)
    {
        String[] request = builder.buildDetailedNotificationRequest(unitResourceId, notificationIds, credentialHolder.getSessionData().getSessionID());
        HttpRequestTask task = new HttpRequestTask(request, new RequestTaskCallback() {
            @Override
            public void onResult(String response) {
                WialonObject[] objects = parser.parseDetailedNotifications(parser.parseJsonObjectFromString(response));
                if(objects.length>0)
                {
                    if (!isWialonError(objects[0])) {
                        callback.onSearchSuccessful(objects);
                    } else callback.onError(((WialonError) objects[0]).getError());
                } else callback.onError(com.worldtrack.wtapi_android.dataformat.Error.NO_ITEMS_FOUND);

            }
        });
        task.execute();
    }

    public void updateNotification(final long unitResourceId,final DetailedNotification notification, final UpdateCallback callback, boolean shouldLoginBefore)
    {
        if (shouldLoginBefore)
            credentialHolder.tokenLogin(credentialHolder.getToken(), new LoginCallback() {
                @Override
                public void onLoginSuccessful(SessionData sessionData) {
                    updateNotificationRequest(unitResourceId,notification, callback);
                }

                @Override
                public void onError(com.worldtrack.wtapi_android.dataformat.Error error) {
                    callback.onError(error);
                }
            });
        else
            updateNotificationRequest(unitResourceId,notification, callback);

    }
    private void updateNotificationRequest(long unitResourceId,DetailedNotification notification, final UpdateCallback callback)
    {
        String[] request = builder.buildNotificationCRUDRequest(credentialHolder.getSessionData().getSessionID(), ActionType.UPDATE, unitResourceId, notification);
        HttpRequestTask task = new HttpRequestTask(request, new RequestTaskCallback() {
            @Override
            public void onResult(String response) {
                WialonObject object = parser.parseCreateUpdateResponseNotification(parser.parseJsonObjectFromString(response));
                if (!isWialonError(object)) {
                    callback.onObjectUpdated(object);
                } else callback.onError(((WialonError) object).getError());

            }
        });
        task.execute();

    }
}
